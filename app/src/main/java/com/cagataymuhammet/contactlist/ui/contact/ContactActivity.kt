package com.cagataymuhammet.contactlist.ui.contact

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.cagataymuhammet.contactlist.R
import com.cagataymuhammet.contactlist.databinding.ActivityContactBinding
import com.cagataymuhammet.contactlist.ui.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactActivity : BaseBindingActivity<ActivityContactBinding>() {


    @LayoutRes
    override fun getContentViewLayoutResId() = R.layout.activity_contact

    override fun populateUI(savedInstanceState: Bundle?) {
        hideUpButton()
    }

    fun showUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun hideUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}