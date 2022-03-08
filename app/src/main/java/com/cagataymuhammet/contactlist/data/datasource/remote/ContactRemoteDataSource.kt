package com.cagataymuhammet.contactlist.data.datasource.remote

import android.util.Log
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.util.constants.Resource
import com.cagataymuhammet.contactlist.util.networking.ContactService
import com.medipol.medipolhafat11mvvm.data.datasource.ContactDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactRemoteDataSource @Inject constructor(var service: ContactService) : ContactDataSource {

    override suspend fun getContacts(): Flow<Resource<List<Contact>>> = flow {
        emit(Resource.Loading(true))
        try {
            emit(Resource.Success(service.getContacts()))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun addContact(contactBody: Contact): Flow<Resource<Contact>> = flow {
        emit(Resource.Loading(true))
        try {
            emit(Resource.Success(service.addContact(contactBody)))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun deleteContact(contactID: String): Flow<Resource<Contact>> = flow {
        emit(Resource.Loading(true))

        try {
            emit(Resource.Success(service.deleteContact(contactID)))
         } catch (ex: Exception) {
             emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun editContact(
        contactBody: Contact
    ): Flow<Resource<Contact>> = flow {
        emit(Resource.Loading(true))
        try {
            emit(Resource.Success(service.editContact(contactBody, contactBody.id!!)))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage))
        }
    }
}