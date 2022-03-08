package com.cagataymuhammet.contactlist.util.extentions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout

fun ImageView.loadFromUrl(url: String?) {

    url?.let {
        Glide.with(this.context)
            .load(url)
            // .error(R.drawable.interneticon)
            .centerCrop()
            .into(this)
    }

}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun TextInputLayout.isTextEmpty(): Boolean {
    return this.editText?.text.toString().trim().isNullOrEmpty()
}
