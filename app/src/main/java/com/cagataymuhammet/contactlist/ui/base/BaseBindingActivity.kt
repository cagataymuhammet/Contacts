package com.cagataymuhammet.contactlist.ui


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cagataymuhammet.contactlist.ui.base.BaseActivity

abstract class BaseBindingActivity<T : ViewDataBinding> : BaseActivity() {

    protected var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getContentViewLayoutResId())
        mBinding?.lifecycleOwner = this
        populateUI(savedInstanceState)
    }

}

