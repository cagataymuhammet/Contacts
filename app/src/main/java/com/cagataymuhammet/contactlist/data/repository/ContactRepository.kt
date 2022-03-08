package com.medipol.medipolhafat11mvvm.data.repository


import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.util.constants.Resource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun syncContacts()

    suspend fun getContacts(): Flow<Resource<List<Contact>>>

    suspend fun addContact(contactBody: Contact): Flow<Resource<Contact>>

    suspend fun deleteContact(contactID: String): Flow<Resource<Contact>>

    suspend fun editContact(contactBody: Contact): Flow<Resource<Contact>>

}