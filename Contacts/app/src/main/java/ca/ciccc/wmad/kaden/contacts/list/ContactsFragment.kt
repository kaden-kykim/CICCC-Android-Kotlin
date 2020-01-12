package ca.ciccc.wmad.kaden.contacts.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.ciccc.wmad.kaden.contacts.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by lazy {
        ViewModelProviders.of(this).get(ContactsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactsBinding.inflate(inflater)
        val adapter = ContactAdapter()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.contactList.adapter = adapter

        viewModel.contactsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addIndexAndSubmitList(it)
            }
        })

        return binding.root
    }


}
