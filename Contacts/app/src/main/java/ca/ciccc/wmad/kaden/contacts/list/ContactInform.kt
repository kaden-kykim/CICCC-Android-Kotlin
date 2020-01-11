package ca.ciccc.wmad.kaden.contacts.list

import androidx.core.text.isDigitsOnly
import com.google.gson.annotations.SerializedName
import java.util.*

private const val NA = "Undefined"

data class ContactList(
    @SerializedName("results")
    val contactList: List<Contact>
)

data class Contact(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: Name,
    @SerializedName("location")
    val location: Location,
    @SerializedName("email")
    val email: String,
    @SerializedName("cell")
    val cell: String
) {
    fun genContact(first: String, last: String, cell: String): Contact {
        return Contact(NA, Name(first, last), Location(Street(.0, NA), NA, NA, NA), NA, cell)
    }

    companion object {
        fun isCellAvailable(cell: String): Boolean {
            return (cell.length == 10) && cell.isDigitsOnly()
        }

        fun isNameAvailable(name: String): Boolean {
            return StringTokenizer(name, " ").countTokens() == 2
        }
    }
}

data class Name(
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
)

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