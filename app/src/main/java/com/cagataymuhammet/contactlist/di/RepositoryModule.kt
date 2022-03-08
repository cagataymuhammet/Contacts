package com.cagataymuhammet.contactlist.di

import android.content.Context
import com.cagataymuhammet.contactlist.data.datasource.locale.ContactLocaleDataSource
import com.cagataymuhammet.contactlist.data.datasource.remote.ContactRemoteDataSource
import com.cagataymuhammet.contactlist.data.db.ContactDao
import com.medipol.medipolhafat11mvvm.data.datasource.ContactDataSource
import com.medipol.medipolhafat11mvvm.data.repository.ContactRepository
import com.medipol.medipolhafat11mvvm.data.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideContactRepository(@ApplicationContext appContext: Context,
                                 remoteDataSource: ContactRemoteDataSource,
                                 localeDataSource: ContactLocaleDataSource,
                                  contactDao: ContactDao): ContactRepository {
        return ContactRepositoryImpl(appContext,remoteDataSource,localeDataSource,contactDao)
    }

}
