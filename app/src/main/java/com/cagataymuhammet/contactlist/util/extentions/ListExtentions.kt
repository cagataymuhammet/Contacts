package com.cagataymuhammet.contactlist.util.extentions

import android.os.AsyncTask
import androidx.paging.PagedList
import com.cagataymuhammet.contactlist.data.datasource.ContactPositionalDataSource
import com.cagataymuhammet.contactlist.util.UiThreadExecutor
import com.cagataymuhammet.contactlist.util.constants.Constants


val pagedListConfig = PagedList.Config.Builder()
    .setPageSize(Constants.PAGE_SIZE)
    .build()

@Synchronized
fun <T> List<T>.toPagedList(): PagedList<T> {
    return PagedList.Builder(ContactPositionalDataSource(this), pagedListConfig)
        .setNotifyExecutor(UiThreadExecutor())
        .setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        .build()
}



