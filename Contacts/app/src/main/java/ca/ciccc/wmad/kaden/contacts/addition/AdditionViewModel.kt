package ca.ciccc.wmad.kaden.contacts.addition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.ciccc.wmad.kaden.contacts.network.Contact

class AdditionViewModel : ViewModel() {

    private val _navigateToContactList = MutableLiveData<Contact>()
    val navigateToContactList: LiveData<Contact>
        get() = _navigateToContactList

    fun onSubmitClicked(contact: Contact) {
        _navigateToContactList.value = contact
    }

    fun onSubmitNavigated() {
        _navigateToContactList.value = null
    }

}
