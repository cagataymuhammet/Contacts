package com.cagataymuhammet.contactlist.ui.contact.list


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cagataymuhammet.contactlist.data.model.Contact
import com.cagataymuhammet.contactlist.util.MainCoroutineScopeRule
import com.cagataymuhammet.contactlist.util.constants.Resource
import com.cagataymuhammet.contactlist.util.getOrAwaitValue
import com.google.common.truth.Truth
import com.medipol.medipolhafat11mvvm.data.repository.ContactRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ContactListViewModelTest {

    lateinit var viewModel: ContactListViewModel

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineScopeRule()

    @MockK
    lateinit var repository: ContactRepository

    private val exceptionMockData = mockk<Exception>(relaxed = true)
    private val successContactListResponseMockData = mockk<List<Contact>>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ContactListViewModel(repository)
    }


    @Test
    fun `when network request is sucesss`() = runBlockingTest {
        //GIVEN
        coEvery {
            repository.getContacts()
        } returns flow {
            emit(Resource.Success(successContactListResponseMockData))
        }

        //WHEN
        viewModel.getContacts()

        Truth.assertThat(viewModel.contactResponseLiveData.getOrAwaitValue())
            .isEqualTo(successContactListResponseMockData)
    }


    @Test
    fun `when network request is loading`() {

        //GIVEN
        coEvery {
            repository.getContacts()
        } returns flow {
            emit(Resource.Loading(true))
        }

        //WHEN
        viewModel.getContacts()

        //THEN
        Truth.assertThat(viewModel.isLoading.getOrAwaitValue()).isEqualTo(true)
    }

    @Test
    fun `when network request throws exception`() {

        //GIVEN
        coEvery {
            repository.getContacts()
        } returns flow {
            emit(Resource.Error(exceptionMockData))
        }

        //WHEN
        viewModel.getContacts()

        //THEN
        Truth.assertThat(viewModel.exceptionLiveData.getOrAwaitValue()).isEqualTo(exceptionMockData)
    }
}