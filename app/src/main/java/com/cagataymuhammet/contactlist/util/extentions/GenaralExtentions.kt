package com.cagataymuhammet.contactlist.util.extentions

import android.content.Context
import com.cagataymuhammet.contactlist.data.db.ContactEntity
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.util.constants.DbSyncType

fun List<ContactEntity>.toContactList():List<Contact>
{
    val contactList = mutableListOf<Contact>()
    this.forEach {
        contactList.add(it.toContact())
    }
    return contactList
}

fun  ContactEntity.toContact(): Contact{

    return  Contact(
        id =this.contactID,
        name =this.name,
        surname =this.surname,
        number = this.number,
        email = this.email,
        companyName =this.companyName,
        department = this.department,
        createdAt =this.createdAt
    )
}

fun  Contact.toEntity(dbSyncType: DbSyncType, createDate:String?=null): ContactEntity{

    var date =this.createdAt
    if(!createDate.isNullOrBlank()) {
        date =this.createdAt
    }

    return  ContactEntity(
        name =this.name,
        surname =this.surname,
        number = this.number,
        email = this.email,
        companyName =this.companyName,
        department = this.department,
        createdAt =date,
        contactID = this.id,
        syncType = dbSyncType.value
    )
}