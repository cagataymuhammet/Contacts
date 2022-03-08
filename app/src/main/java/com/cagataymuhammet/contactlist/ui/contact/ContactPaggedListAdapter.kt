package com.cagataymuhammet.contactlist.ui.contact


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.databinding.ItemContactBinding
import com.cagataymuhammet.contactlist.util.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactPaggedListAdapter @Inject constructor(val appContext: Context) :
    PagedListAdapter<Contact, ContactPaggedListAdapter.ViewHolder?>(
        Utils.contactDiffUtil
    ) {

    private var onItemClickListener: ((Contact) -> Unit)? = null

    fun setOnItemClickListener(listener: (Contact) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder constructor(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {

            binding.apply {
                txtName.text = "${contact.name}  ${contact.surname}"
                txtNumber.text = contact.number.toString()
                imgProfile.setImageDrawable(contact.id?.let {
                    Utils.getImageByIndex(
                        imgProfile.context,
                        it.toInt()
                    )
                })

                item.setOnClickListener {
                    onItemClickListener?.let {
                        it(contact)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

}