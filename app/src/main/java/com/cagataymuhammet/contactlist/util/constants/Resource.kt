package com.cagataymuhammet.contactlist.util.constants


sealed class Resource<out T> {
    data class Loading(val loading: Boolean) : Resource<Nothing>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Success<T>(val data: T?) : Resource<T>()
}