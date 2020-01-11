package ca.ciccc.wmad.kaden.contacts.list

import androidx.core.text.isDigitsOnly
import com.google.gson.annotations.SerializedName
import java.util.*

private const val INIT_STR = "Undefined"
private const val INIT_NUM = 0.0

data class ContactList(
    @SerializedName("results")
    val contactList: List<Contact>
)

data class Contact(
    @SerializedName("gender")
    val gender: String = INIT_STR,
    @SerializedName("name")
    val name: Name = Name(INIT_STR, INIT_STR),
    @SerializedName("location")
    val location: Location = Location(Street(INIT_NUM, INIT_STR), INIT_STR, INIT_STR, INIT_STR),
    @SerializedName("email")
    val email: String = INIT_STR,
    @SerializedName("cell")
    val cell: String = INIT_STR,
    @SerializedName("login")
    val login: Login = Login(UUID.randomUUID().toString(), INIT_STR)
) {
    fun genContact(first: String, last: String, cell: String): Contact {
        return Contact(name = Name(first, last), cell = cell)
    }

    companion object {
        fun isCellAvailable(cell: String) = (cell.length == 10) && cell.isDigitsOnly()
        fun isNameAvailable(name: String) = StringTokenizer(name, " ").countTokens() == 2
    }
}

data class Name(
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
) {
    fun getFullName() = "$first $last"
}

data class Location(
    @SerializedName("street")
    val street: Street,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val province: String,
    @SerializedName("postcode")
    val postCode: String
)

data class Street(
    @SerializedName("number")
    val number: Double,
    @SerializedName("name")
    val name: String
)

data class Login(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("username")
    val userName: String
)