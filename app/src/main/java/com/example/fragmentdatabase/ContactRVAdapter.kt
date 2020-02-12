package com.example.fragmentdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactRVAdapter(var contactList : ArrayList<Contact>, val callback: ContactCallback) :
RecyclerView.Adapter<ContactRVAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.populatePersonItem(contactList[position])

    override fun getItemCount() = contactList.size

    fun updateList(passedList : ArrayList<Contact>) {
        contactList = passedList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun populatePersonItem(contact : Contact) {
            itemView.tvFirstName.text = contact.firstName
            itemView.tvLastName.text = contact.lastName
            itemView.tvPhoneNumber.text = contact.phoneNumber
            itemView.setOnClickListener{ callback.passContact(contact) }
        }
    }
}