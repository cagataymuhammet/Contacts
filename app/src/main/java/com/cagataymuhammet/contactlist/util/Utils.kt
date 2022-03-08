package com.cagataymuhammet.contactlist.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.cagataymuhammet.contactlist.R
import com.cagataymuhammet.contactlist.data.model.Contact

object Utils {

    fun getImageByIndex(context: Context, index: Int): Drawable? {
        return when (index % 6) {
            0 -> ContextCompat.getDrawable(context, R.drawable.p1)
            1 -> ContextCompat.getDrawable(context, R.drawable.p2)
            2 -> ContextCompat.getDrawable(context, R.drawable.p3)
            3 -> ContextCompat.getDrawable(context, R.drawable.p4)
            4 -> ContextCompat.getDrawable(context, R.drawable.p5)
            else -> ContextCompat.getDrawable(context, R.drawable.p6)
        }
    }


    val contactDiffUtil: DiffUtil.ItemCallback<Contact> =
        object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(
                oldItem: Contact,
                newItem: Contact
            ): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: Contact,
                newItem: Contact
            ): Boolean {
                return oldItem == newItem
            }
        }
}