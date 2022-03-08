package com.cagataymuhammet.contactlist.di

import android.content.Context
import androidx.room.Room
import com.cagataymuhammet.contactlist.data.db.ContactDao
import com.cagataymuhammet.contactlist.data.db.ContactDatabase
import com.cagataymuhammet.contactlist.util.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideContactDao(contactDatabase: ContactDatabase): ContactDao {
        return contactDatabase.contactDao()
    }

    @Provides
    @Singleton
    fun provideContactDatabase(@ApplicationContext appContext: Context): ContactDatabase {
        return Room.databaseBuilder(
            appContext,
            ContactDatabase::class.java,
            Constants.CONTACT_ROOM_DB_NAME
        ).allowMainThreadQueries().build()
    }

}