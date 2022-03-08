package com.cagataymuhammet.contactlist.util.networking

import android.content.Context
import android.net.NetworkInfo

import android.net.ConnectivityManager


object NetworkUtil {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null &&
                activeNetwork.isConnected
    }
}