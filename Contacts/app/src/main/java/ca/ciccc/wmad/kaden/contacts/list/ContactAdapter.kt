package ca.ciccc.wmad.kaden.contacts.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.ciccc.wmad.kaden.contacts.R
import ca.ciccc.wmad.kaden.contacts.databinding.ListViewContactItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ITEM_VIEW_TYPE_INDEX = 0
const val ITEM_VIEW_TYPE_CONTACT = 1

class ContactAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(ContactDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_INDEX -> IndexViewHolder.from(parent)
            ITEM_VIEW_TYPE_CONTACT -> ContactViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactViewHolder -> {
                val contact = getItem(position) as DataItem.ContactItem
                holder.bind(contact.contact)
            }
            is IndexViewHolder -> {
                holder.bind(getItem(position).id)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.IndexItem -> ITEM_VIEW_TYPE_INDEX
            is DataItem.ContactItem -> ITEM_VIEW_TYPE_CONTACT
        }
    }

    fun addIndexAndSubmitList(list: List<Contact>) {
        adapterScope.launch {
            val contactList = list.sortedWith(Comparator { contact1: Contact, contact2: Contact ->
                (contact1.name.first.compareTo(contact2.name.first))
            })

            val items = ArrayList<DataItem>()
            var indexChar = contactList[0].name.first[0].toUpperCase()
            items.add(DataItem.IndexItem(indexChar))
            for (item in contactList) {
                val char = item.name.first[0].toUpperCase()
                if (char != indexChar) {
                    indexChar = char
                    items.add(DataItem.IndexItem(indexChar))
                }
                items.add(DataItem.ContactItem(item))
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class ContactViewHolder private constructor(private var binding: ListViewContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.contact = contact
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ContactViewHolder {
                return ContactViewHolder(
                    ListViewContactItemBinding
                        .inflate(LayoutInflater.from(parent.context))
                )
            }
        }
    }

    class IndexViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(index: String) {
            view.findViewById<TextView>(R.id.textView_index).text = index
        }

        companion object {
            fun from(parent: ViewGroup): IndexViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_view_index_item, parent, false)
                return IndexViewHolder(view)
            }
        }
    }
}

class ContactDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val id: String

    data class ContactItem(val contact: Contact) : DataItem() {
        override val id = contact.login.uuid
    }

    data class IndexItem(val index: Char) : DataItem() {
        override val id = index.toString()
    }
}