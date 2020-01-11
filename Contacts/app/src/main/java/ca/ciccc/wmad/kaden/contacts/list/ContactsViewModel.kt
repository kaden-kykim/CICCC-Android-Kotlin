package ca.ciccc.wmad.kaden.contacts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.ciccc.wmad.kaden.contacts.network.ContactApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val NUM_OF_CONTACTS = 30

class ContactsViewModel : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>>
        get() = _contactsList

    init {
        getContacts(NUM_OF_CONTACTS)
    }

    private fun getContacts(number: Int) {
        coroutineScope.launch {
            val getContactsDeferred = ContactApi.retrofitService.getContactsAsync(number)
            try {
                val result = getContactsDeferred.await()
                _contactsList.value = result.contactList
                    .sortedWith(Comparator { contact1: Contact, contact2: Contact ->
                        (contact1.name.first.compareTo(contact2.name.first))
                    })
            } catch (e: Exception) {
                _contactsList.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}