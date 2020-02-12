package com.example.fragmentdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_contact_entry.*

class MainActivity : AppCompatActivity(),
        ContactListFragment.OnFragmentInteractionListener,
        ContactEntryFragment.OnFragmentInteractionListener
{
    val databaseHelper by lazy { ContactDatabaseHelper(this) }
    val contractEntryFrag by lazy { ContactEntryFragment() }
    val contactListFrag by lazy { ContactListFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //databaseHelper.insertContactIntoDatabase(Contact("Andrew", "Millsap", "6147956360"))

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fgContactList, contactListFrag)
            .addToBackStack("CONTACT_LIST_FRAG")
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fgContactEntry, contractEntryFrag)
            .addToBackStack("CONTACT_ENTRY_FRAG")
            .commit()

    }

    override fun onContentListInteraction(
        firstName: String,
        lastName: String,
        phoneNumber: String)
    {
        contractEntryFrag.etFirstNameInput.setText(firstName)
        contractEntryFrag.etLastNameInput.setText(lastName)
        contractEntryFrag.etPhoneNumberInput.setText(phoneNumber)
    }

    override fun onContactEntryInteraction(
        firstName: String,
        lastName: String,
        phoneNumber: String)
    {
        if(databaseHelper.getOneContactFromDatabase(phoneNumber).toString() == phoneNumber)
        {
            databaseHelper.updatePersonInDatabase(Contact(firstName, lastName, phoneNumber))
            contactListFrag.adapter.updateList(databaseHelper.getAllContactsFromDatabase())
        }
        else
        {
            databaseHelper.insertContactIntoDatabase(Contact(firstName, lastName, phoneNumber))
            contactListFrag.adapter.updateList(databaseHelper.getAllContactsFromDatabase())

        }
    }

}
