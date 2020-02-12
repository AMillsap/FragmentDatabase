package com.example.fragmentdatabase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contact_list.*

class ContactListFragment : Fragment(), ContactCallback {
    private var listener: OnFragmentInteractionListener? = null
    val databaseHelper by lazy{ContactDatabaseHelper(context as Context)}
    val adapter by lazy {ContactRVAdapter(databaseHelper.getAllContactsFromDatabase(), this)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initRecyclerView()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener
    {
        fun onContentListInteraction(firstName : String, lastName : String, phoneNumber : String)
    }

    override fun passContact(contact: Contact) {
        listener?.onContentListInteraction(contact.firstName, contact.lastName, contact.phoneNumber)
    }

    fun initRecyclerView()
    {
        rvContactList.layoutManager = LinearLayoutManager(context)
        rvContactList.adapter = adapter
    }

}
