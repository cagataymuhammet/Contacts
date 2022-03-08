package com.cagataymuhammet.contactlist.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cagataymuhammet.contactlist.R
import com.google.android.material.snackbar.Snackbar


abstract class BaseBindingFragment<T : ViewDataBinding> : BaseFragment() {

    protected var mBinding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        mBinding = view?.let { DataBindingUtil.bind(it) }
        mBinding?.lifecycleOwner = viewLifecycleOwner

        return view
    }


}
