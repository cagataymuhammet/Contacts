package com.cagataymuhammet.contactlist.di

import android.content.Context
import com.cagataymuhammet.contactlist.ui.contact.ContactPaggedListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContactPaggedListAdapter(@ApplicationContext appContext: Context): ContactPaggedListAdapter {
        return ContactPaggedListAdapter(appContext)
    }
}