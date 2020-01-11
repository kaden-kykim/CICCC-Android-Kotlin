package ca.ciccc.wmad.kaden.contacts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.ciccc.wmad.kaden.contacts.network.ContactApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val NUM_OF_CONTACTS = 5

class ContactsViewModel : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _contactsList = MutableLiveData<ContactList>()
    val contactsList: LiveData<ContactList>
        get() = _contactsList

    // Temporal Variables: response
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getContacts(NUM_OF_CONTACTS)
    }

    private fun getContacts(number: Int) {
        coroutineScope.launch {
            val getContactsDeferred = ContactApi.retrofitService.getContactsAsync(number)
            try {
                val result = getContactsDeferred.await()
                _contactsList.value = result
                _response.value = "Success: ${result.contactList.size} Contacts transmitted"
            } catch (e: Exception) {
                _contactsList.value = ContactList(ArrayList())
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}