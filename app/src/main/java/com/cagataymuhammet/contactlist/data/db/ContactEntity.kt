package com.cagataymuhammet.contactlist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cagataymuhammet.contactlist.util.constants.Constants
import com.cagataymuhammet.contactlist.util.constants.DbStatus
import com.cagataymuhammet.contactlist.util.constants.DbSyncType


@Entity(tableName = Constants.CONTACT_ROOM_TABLE_NAME)
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var companyName: String? = null,
    var createdAt: String? = null,
    var department: String? = null,
    var email: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var number: Int? = null,
    var contactID: String? = null,
    var syncType: Int? = DbSyncType.None.value,
    var status: Int? =DbStatus.Active.value
)