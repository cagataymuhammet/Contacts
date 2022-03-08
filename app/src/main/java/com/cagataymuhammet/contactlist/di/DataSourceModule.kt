package com.turkcell.digitalBrand.module

import com.cagataymuhammet.contactlist.data.datasource.remote.ContactRemoteDataSource
import com.medipol.medipolhafat11mvvm.data.datasource.ContactDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    /*
    @Binds
    @Singleton
    abstract fun bindContactDataSource(
        dataSource: ContactRemoteDataSource
    ): ContactDataSource

     */


}