package com.cagataymuhammet.contactlist.data.db

import androidx.room.*
import com.cagataymuhammet.contactlist.util.constants.Constants
import com.cagataymuhammet.contactlist.util.constants.DbSyncType


@Dao
interface ContactDao {

    @Query("SELECT COUNT(*) FROM ${Constants.CONTACT_ROOM_TABLE_NAME}")
    fun getAllCount():Int

    @Query("SELECT * FROM ${Constants.CONTACT_ROOM_TABLE_NAME} WHERE syncType<>0")
    fun getAllNeedSyncContacts(): List<ContactEntity>

    @Query("DELETE FROM ${Constants.CONTACT_ROOM_TABLE_NAME}")
    fun deleteAllContacts()

    @Query("UPDATE ${Constants.CONTACT_ROOM_TABLE_NAME} SET  status=0 and syncType=3 WHERE contactID=:contactID")
    fun deleteByContactID(contactID: String)

    @Query("SELECT * FROM ${Constants.CONTACT_ROOM_TABLE_NAME} where status=1")
    fun getAllContacts(): List<ContactEntity>

    @Insert
    fun Insert(contact: ContactEntity?)

    @Update
        (onConflict = OnConflictStrategy.REPLACE)
    fun Update(contact: ContactEntity?)

}