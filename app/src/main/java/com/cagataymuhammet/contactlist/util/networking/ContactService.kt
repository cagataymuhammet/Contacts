package com.cagataymuhammet.contactlist.util.networking

import com.cagataymuhammet.contactlist.data.model.Contact
import retrofit2.http.*

interface ContactService {

    //https://621f40f4311a705914063fc9.mockapi.io/interview/v1/contacts

    @GET("contacts")
    suspend fun getContacts(): List<Contact>

    @POST("contacts")
    suspend fun addContact(@Body contactBody: Contact): Contact

    @DELETE("contacts/{contactID}")
    suspend fun deleteContact(@Path("contactID") contactID: String): Contact

    @PUT("contacts/{contactID}")
    suspend fun editContact(
        @Body contactBody: Contact,
        @Path("contactID") contactID: String
    ): Contact

}