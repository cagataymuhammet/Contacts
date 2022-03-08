package com.cagataymuhammet.contactlist.ui.contact.detail

import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cagataymuhammet.contactlist.R
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.databinding.FragmentContactDetailBinding
import com.cagataymuhammet.contactlist.ui.base.BaseBindingFragment
import com.cagataymuhammet.contactlist.util.extentions.gone
import com.cagataymuhammet.contactlist.util.extentions.isTextEmpty
import com.cagataymuhammet.contactlist.util.extentions.show
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ContactDetailFragment : BaseBindingFragment<FragmentContactDetailBinding>() {


    var textInputLayouts = mutableListOf<TextInputLayout>()

    private val contactDetailViewModel: ContactDetailViewModel by viewModels()

    var contactItem: Contact? = null

    @LayoutRes
    override fun getContentLayoutResId() = R.layout.fragment_contact_detail

    override fun populateUI() {

        initUI()
        fillDatas()
        initObservers()
    }

    private  fun showSuccessfullToast()
    {
        Toast.makeText(requireContext(),R.string.successfull,Toast.LENGTH_LONG).show()
    }

    private fun initObservers()
    {
        contactDetailViewModel.apply {

            errorMessageLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    mBinding?.apply {
                        Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                    }
                }
            })

            deleteAnswer.observe(viewLifecycleOwner, Observer {
                showSuccessfullToast()
            })

            updateAnswer.observe(viewLifecycleOwner, Observer {
                showSuccessfullToast()
            })

            addAnswer.observe(viewLifecycleOwner, Observer {
                showSuccessfullToast()
            })

        }

    }

    private fun clearAllErrors() {
        textInputLayouts.forEach {
            if (it.isErrorEnabled) {
                it.isErrorEnabled = false
            }
        }
    }

    private fun isAllEditsFilled(): Boolean {
        clearAllErrors()
        var count = 0
        textInputLayouts.forEach {
            if (it.isTextEmpty()) {
                count++
                it.error = getString(R.string.input_validation)
                it.isErrorEnabled = true
            }
        }
        return count == 0
    }

    private fun clearInputs() {
        mBinding?.apply {

            linerLayoutButtons.gone()
            btnAddContact.show()

            textInputLayouts.forEach {
                it.editText?.text?.clear()
            }
        }
    }

    private fun initUI() {
        activity?.title = getString(R.string.contact_card)
        mBinding?.apply {

            textInputLayouts = mutableListOf(
                txtInputName,
                txtInputSurname,
                txtInputNumber,
                txtInputEmail,
                txtInputCompany,
                txtInputDepartment
            )

            viewModel = contactDetailViewModel

            btnAddContact.setOnClickListener {

                if (isAllEditsFilled()) {

                    runBlocking {
                        setDatas()
                    }
                    contactDetailViewModel.addCard(contactItem)
                }
            }

            btnDelete.setOnClickListener {
                contactItem?.id?.let {
                    contactDetailViewModel.deleteCard(it)
                    clearInputs()
                }
            }

            btnUpdate.setOnClickListener {

                if (isAllEditsFilled()) {
                    runBlocking {
                        setDatas()
                    }
                    contactDetailViewModel.updateCard(contactItem)
                }
            }
        }
    }

    private fun setDatas() {

        if(contactItem==null)
        {
            contactItem= Contact("","","","","","",-1,"")
        }

        mBinding?.apply {


            txtInputName.editText?.text?.let {
                contactItem?.name = it.toString()
            }

            txtInputSurname.editText?.text?.let {
                contactItem?.surname = it.toString()
            }

            txtInputNumber.editText?.text?.let {

                if (!it.toString().isNullOrBlank()) {
                    it.toString().toInt().let {
                        contactItem?.number = it
                    }
                }
            }

            txtInputEmail.editText?.text?.let {
                contactItem?.email = it.toString()
            }

            txtInputCompany.editText?.text?.let {
                contactItem?.companyName = it.toString()
            }

            txtInputDepartment.editText?.text?.let {
                contactItem?.department = it.toString()
            }
        }

    }

    private fun fillDatas() {
        mBinding?.apply {
            arguments?.apply {
                contactItem = ContactDetailFragmentArgs.fromBundle(this).contactResultArgument
                contactItem?.also {
                    linerLayoutButtons.show()
                    btnAddContact.gone()
                    txtInputName.editText?.setText(it.name)
                    txtInputSurname.editText?.setText(it.surname)
                    txtInputNumber.editText?.setText(it.number.toString())
                    txtInputEmail.editText?.setText(it.email)
                    txtInputCompany.editText?.setText(it.companyName)
                    txtInputDepartment.editText?.setText(it.department)
                } ?: kotlin.run {
                    linerLayoutButtons.gone()
                    btnAddContact.show()
                }
            }
        }

    }

}