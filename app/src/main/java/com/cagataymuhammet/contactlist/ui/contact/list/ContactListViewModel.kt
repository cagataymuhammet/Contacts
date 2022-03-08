package com.cagataymuhammet.contactlist.ui.contact.list


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.ui.base.BaseViewModel
import com.cagataymuhammet.contactlist.util.constants.Resource
import com.cagataymuhammet.contactlist.util.extentions.toPagedList
import com.cagataymuhammet.contactlist.util.extentions.toTrLowerCase
import com.medipol.medipolhafat11mvvm.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(private val repository: ContactRepository) :
    BaseViewModel() {

    init {
        viewModelScope.launch {
            repository.syncContacts()
        }
    }

    var filteredContactList = arrayListOf<Contact>()

    fun getFilter(query: String): PagedList<Contact> {
        val filteredList = filteredContactList.filter {
            it.name?.toTrLowerCase()!!.contains(query.toTrLowerCase())
                    || it.surname?.toTrLowerCase()!!.contains(query.toTrLowerCase())
        }

        return filteredList.toPagedList()
    }

    var contactResponsePagedLiveData = MutableLiveData<PagedList<Contact>>()
    var contactResponseLiveData = MutableLiveData<List<Contact>>()

    fun getContacts() {

        viewModelScope.launch {

            repository.getContacts().collect {

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

                        isLoading.postValue(false)
                        isPageLoaded.postValue(true)

                        it.data?.let {
                            contactResponseLiveData.postValue(it)

                            if (it.isNotEmpty()) {

                                filteredContactList.addAll(it)
                                contactResponsePagedLiveData.postValue(it.toPagedList())
                            }
                        }
                    }
                }
            }
        }
    }



}

