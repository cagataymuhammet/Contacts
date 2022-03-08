package com.cagataymuhammet.contactlist.ui.contact.detail


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.ui.base.BaseViewModel
import com.cagataymuhammet.contactlist.util.SingleLiveEvent
import com.cagataymuhammet.contactlist.util.constants.Resource
import com.medipol.medipolhafat11mvvm.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val repository: ContactRepository) :
    BaseViewModel() {


    var addAnswer = SingleLiveEvent<Boolean>()
    var deleteAnswer = SingleLiveEvent<Boolean>()
    var updateAnswer = SingleLiveEvent<Boolean>()

    fun deleteCard(id: String) {

         viewModelScope.launch {
            repository.deleteContact(id).collect {

                when (it) {

                    is Resource.Loading -> {
                        isPageLoaded.postValue(false)
                        isLoading.postValue(true)
                    }

                    is Resource.Error -> {
                        isLoading.postValue(false)
                        errorMessageLiveData.postValue(it.errorMessage)
                    }

                    is Resource.Success -> {
                        deleteAnswer.postValue(true)
                        isLoading.postValue(false)
                        isPageLoaded.postValue(true)
                    }
                }
            }

        }
    }

    fun addCard(contact: Contact?) {
         viewModelScope.launch {
            contact?.let {
                repository.addContact(it).collect {
                    when (it) {

                        is Resource.Loading -> {
                            isPageLoaded.postValue(false)
                            isLoading.postValue(true)
                        }

                        is Resource.Error -> {
                            isLoading.postValue(false)
                            errorMessageLiveData.postValue(it.errorMessage)
                        }

                        is Resource.Success -> {
                            addAnswer.postValue(true)
                            isLoading.postValue(false)
                            isPageLoaded.postValue(true)
                        }
                    }
                }
            }

        }
    }

    fun updateCard(contact: Contact?) {
        viewModelScope.launch {
            contact?.let {
                repository.editContact(it).collect {
                    when (it) {
                        is Resource.Loading -> {
                            isPageLoaded.postValue(false)
                            isLoading.postValue(true)
                        }

                        is Resource.Error -> {
                            isLoading.postValue(false)
                            errorMessageLiveData.postValue(it.errorMessage)
                        }

                        is Resource.Success -> {
                            isPageLoaded.postValue(true)
                            isLoading.postValue(false)
                            updateAnswer.postValue(true)
                        }
                    }
                }
            }
        }
    }

}

