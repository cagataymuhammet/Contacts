package com.medipol.medipolhafat11mvvm.data.repository

import android.content.Context
import android.util.Log
import com.cagataymuhammet.contactlist.data.datasource.locale.ContactLocaleDataSource
import com.cagataymuhammet.contactlist.data.datasource.remote.ContactRemoteDataSource
import com.cagataymuhammet.contactlist.data.db.ContactDao
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.util.constants.DbSyncType
import com.cagataymuhammet.contactlist.util.networking.NetworkUtil
import com.cagataymuhammet.contactlist.util.constants.Resource
import com.cagataymuhammet.contactlist.util.extentions.toContact
import com.cagataymuhammet.contactlist.util.extentions.toEntity
import com.cagataymuhammet.contactlist.util.extentions.toPagedList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepositoryImpl @Inject constructor(
    private val context: Context,
    private val remoteDataSource: ContactRemoteDataSource,
    private val localeDataSource: ContactLocaleDataSource,
    private val contactDao: ContactDao
) :
    ContactRepository {

    override suspend fun syncContacts() = withContext(Dispatchers.IO + NonCancellable)
    {
        if(NetworkUtil.isNetworkConnected(context))
        {
            if(contactDao.getAllCount()==0)
            {
                remoteDataSource.getContacts().collect {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let {
                                it.forEach {
                                    runBlocking{
                                        contactDao.Insert(it.toEntity(DbSyncType.None))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                contactDao.getAllNeedSyncContacts().let {
                    it.forEach { contactEntitiy ->
                        runBlocking{
                            when(contactEntitiy.syncType)
                            {
                                DbSyncType.Add.value ->
                                {
                                    remoteDataSource.addContact(contactEntitiy.toContact())
                                }

                                DbSyncType.Delete.value ->
                                {
                                    remoteDataSource.deleteContact(contactEntitiy.contactID!!)
                                }

                                DbSyncType.Update.value ->
                                {
                                    remoteDataSource.editContact(contactEntitiy.toContact())
                                }
                            }
                        }
                    }
                }

                contactDao.deleteAllContacts()

                remoteDataSource.getContacts().collect {
                        when (it) {
                            is Resource.Success -> {
                                it.data?.let {
                                    it.forEach {
                                        runBlocking{
                                            contactDao.Insert(it.toEntity(DbSyncType.None))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }


    override suspend fun getContacts(): Flow<Resource<List<Contact>>> {

        if (NetworkUtil.isNetworkConnected(context)) {
            return remoteDataSource.getContacts()
        } else {
            return localeDataSource.getContacts()
        }
    }


    override suspend fun addContact(contactBody: Contact): Flow<Resource<Contact>> {
        if (NetworkUtil.isNetworkConnected(context)) {
            return remoteDataSource.addContact(contactBody)
        } else {
            return localeDataSource.addContact(contactBody)
        }
    }

    override suspend fun deleteContact(contactID: String): Flow<Resource<Contact>> {

        if (NetworkUtil.isNetworkConnected(context)) {
            return remoteDataSource.deleteContact(contactID)
        } else {
            return localeDataSource.deleteContact(contactID)
        }
    }

    override suspend fun editContact(
        contactBody: Contact
    ): Flow<Resource<Contact>> {
        if (NetworkUtil.isNetworkConnected(context)) {
            return remoteDataSource.editContact(contactBody)
        } else {
            return localeDataSource.editContact(contactBody)
        }
    }

}

