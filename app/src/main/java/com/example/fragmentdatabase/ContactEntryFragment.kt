package com.example.fragmentdatabase

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_contact_entry.*
import kotlinx.android.synthetic.main.fragment_contact_entry.view.*


class ContactEntryFragment : Fragment(), View.OnClickListener {
    private var listener: OnFragmentInteractionListener? = null
    val databaseHelper by lazy{ContactDatabaseHelper(context as Context)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.btnModifyContact.setOnClickListener(this)

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

    interface OnFragmentInteractionListener {
        fun onContactEntryInteraction(firstName : String, lastName : String, phoneNumber : String)
    }

    override fun onClick(v: View?)
    {
        listener?.onContactEntryInteraction(
            etFirstNameInput.text.toString(),
            etLastNameInput.text.toString(),
            etPhoneNumberInput.text.toString())

        var toast = Toast.makeText(context, "Contact Updated", Toast.LENGTH_LONG)
        toast. show()
    }

}
