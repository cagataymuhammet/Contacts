package com.cagataymuhammet.contactlist.ui.contact.list

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cagataymuhammet.contactlist.R
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.databinding.FragmentContactListBinding
import com.cagataymuhammet.contactlist.ui.base.BaseBindingFragment
import com.cagataymuhammet.contactlist.ui.contact.ContactPaggedListAdapter
import com.cagataymuhammet.contactlist.util.extentions.gone
import com.cagataymuhammet.contactlist.util.extentions.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContactListFragment : BaseBindingFragment<FragmentContactListBinding>() {

    @LayoutRes
    override fun getContentLayoutResId() = R.layout.fragment_contact_list

    private val contactListViewModel: ContactListViewModel by viewModels()

    @Inject
    lateinit var contactPaggedListAdapter: ContactPaggedListAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contact_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_search)

        (searchItem.getActionView() as SearchView).apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    lastQuery = query
                    doSerch()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    lastQuery = query
                    doSerch()
                    return true
                }
            })
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun doSerch() {
        lastQuery?.apply {
            contactPaggedListAdapter.submitList(contactListViewModel.getFilter(this))
        }
    }


    override fun populateUI() {
        setHasOptionsMenu(true)
        initBinding()
        contactListViewModel.getContacts()
        observers()
    }

    private fun initBinding() {
        mBinding?.apply {

            viewModel = contactListViewModel
            contactPaggedListAdapter.setOnItemClickListener { it ->

                findNavController().navigate(
                    ContactListFragmentDirections.actionToContactDetailFragment(
                        it
                    )
                )
            }

            recylerViewContacts.apply {
                adapter = contactPaggedListAdapter
                itemAnimator = DefaultItemAnimator()
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            btnAdd.setOnClickListener {
                findNavController().navigate(
                    ContactListFragmentDirections.actionToContactDetailFragment(
                        null
                    )
                )
            }
        }
    }

    private fun observers() {
        contactListViewModel.apply {

            contactResponsePagedLiveData.observe(
                viewLifecycleOwner,
                object : Observer<PagedList<Contact>> {
                    override fun onChanged(t: PagedList<Contact>?) {
                        contactPaggedListAdapter.submitList(t)
                    }
                })

            errorMessageLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    mBinding?.apply {
                        showRetrySnackBar(it, recylerViewContacts)
                    }
                }
            })


            isLoading.observe(viewLifecycleOwner, Observer {
                it?.let {
                    mBinding?.apply {
                        if (it) {
                            progressBar.show()
                        } else {
                            progressBar.gone()
                        }
                    }
                }
            })

        }

    }

    fun showRetrySnackBar(errorMessage: String, view: View) {

        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry), object : View.OnClickListener {
                override fun onClick(view: View?) {
                    contactListViewModel.getContacts()
                }
            }).show()
    }


    companion object {
        var lastQuery: String? = ""
    }
}