package com.cagataymuhammet.contactlist.data.datasource.locale

import com.cagataymuhammet.contactlist.data.db.ContactDao
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.util.constants.DbSyncType
import com.cagataymuhammet.contactlist.util.extentions.toContactList
import com.cagataymuhammet.contactlist.util.constants.Resource
import com.cagataymuhammet.contactlist.util.extentions.toEntity
import com.medipol.medipolhafat11mvvm.data.datasource.ContactDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactLocaleDataSource @Inject constructor(private val contactDao: ContactDao) :
    ContactDataSource {

    override suspend fun getContacts(): Flow<Resource<List<Contact>>> = flow {
        emit(Resource.Loading(true))
        try {
            emit(Resource.Success(contactDao.getAllContacts().toContactList()))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun addContact(contactBody: Contact): Flow<Resource<Contact>> = flow {
        emit(Resource.Loading(true))
        try {
            contactDao.Insert(contactBody.toEntity(DbSyncType.Add))
            emit(Resource.Success(null))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun deleteContact(contactID: String): Flow<Resource<Contact>> = flow {
        emit(Resource.Loading(true))
        try {
            contactDao.deleteByContactID(contactID)
            emit(Resource.Success(null))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun editContact(
        contactBody: Contact
    ): Flow<Resource<Contact>> = flow {
        emit(Resource.Loading(true))

        try {
            contactDao.Update(contactBody.toEntity(DbSyncType.Update))
            emit(Resource.Success(null))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage))
        }
    }

}