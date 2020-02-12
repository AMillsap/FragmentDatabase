package com.example.fragmentdatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?)
    {
        db?.execSQL(CREATE_CONTACT_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertContactIntoDatabase(contact: Contact)
    {
        val database = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_FIRST_NAME, contact.firstName)
        contentValues.put(COL_LAST_NAME, contact.lastName)
        contentValues.put(COL_PHONE_NUMBER, contact.phoneNumber)

        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    fun getOneContactFromDatabase(phoneNumber : String) : Contact?
    {
        val database = readableDatabase
        var contact : Contact? = null
        val cursor = database
            .rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_PHONE_NUMBER = '$phoneNumber'",
                null)

        if(cursor.moveToFirst()) {
            val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
            val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(COL_PHONE_NUMBER))
            contact = Contact(firstName, lastName, phoneNumber)
        }
        cursor.close()
        database.close()
        return contact
    }

    fun getAllContactsFromDatabase() : ArrayList<Contact> {
        val database = readableDatabase
        var contactList : ArrayList<Contact> = ArrayList<Contact>()
        val cursor = database
            .rawQuery("SELECT * FROM $TABLE_NAME",
                null)

        if(cursor.moveToFirst()) {
            do {
                val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(COL_PHONE_NUMBER))
                val contact = Contact(firstName, lastName, phoneNumber)
                contactList.add(contact)
            } while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return contactList
    }

    fun updatePersonInDatabase(contact : Contact) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_FIRST_NAME, contact.firstName)
        contentValues.put(COL_LAST_NAME, contact.lastName)
        contentValues.put(COL_PHONE_NUMBER, contact.phoneNumber)

        database.update(TABLE_NAME, contentValues, "$COL_PHONE_NUMBER = ?", arrayOf(contact.phoneNumber))
        database.close()
    }

    fun removePersonFromDatabase(phoneNumber : String) {
        val database = writableDatabase
        database.delete(TABLE_NAME, "$COL_PHONE_NUMBER = ?", arrayOf(phoneNumber))
        database.close()
    }
}