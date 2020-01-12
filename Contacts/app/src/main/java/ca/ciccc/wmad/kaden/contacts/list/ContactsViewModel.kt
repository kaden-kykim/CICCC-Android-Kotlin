package ca.ciccc.wmad.kaden.contacts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.ciccc.wmad.kaden.contacts.network.Contact
import ca.ciccc.wmad.kaden.contacts.network.ContactApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val NUM_OF_CONTACTS = 30

private var contactDB: List<Contact>? = null

class ContactsViewModel : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>>
        get() = _contactsList

    private val _navigateToAddContact = MutableLiveData<Boolean>()
    val navigateToAddContact: LiveData<Boolean>
        get() = _navigateToAddContact

    init {
        contactDB?.let { _contactsList.value = it } ?: getContacts(NUM_OF_CONTACTS)
    }

    private fun getContacts(number: Int) {
        coroutineScope.launch {
            val getContactsDeferred = ContactApi.retrofitService.getContactsAsync(number)
            try {
                val contactList = getContactsDeferred.await().contactList
                _contactsList.value = contactList
                contactDB = contactList
            } catch (e: Exception) {
                _contactsList.value = ArrayList()
            }
        }
    }

    fun addContact(contact: Contact) {
        _contactsList.value?.let {
            _contactsList.value = it + contact
            contactDB = _contactsList.value
        }
    }

    fun onAddContactClicked() {
        _navigateToAddContact.value = true
    }

    fun onAddContactNavigated() {
        _navigateToAddContact.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}