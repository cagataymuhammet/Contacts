package com.cagataymuhammet.contactlist.ui.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.cagataymuhammet.contactlist.ContactListApp
import com.cagataymuhammet.contactlist.ui.BaseBindingActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getContentViewLayoutResId(): Int

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    lateinit var contactListApp: ContactListApp

    abstract fun populateUI(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactListApp = appContext as ContactListApp

        if (this !is BaseBindingActivity<*>) {
            setContentView(getContentViewLayoutResId())
            populateUI(savedInstanceState)
        }
    }

}

