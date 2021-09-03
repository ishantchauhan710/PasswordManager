package com.ishant.passwordmanager.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetails

class PasswordManagerRepository(val db: PasswordManagerDatabase) {
    suspend fun upsertEntry(entry: Entry) = db.getPasswordManagerDao().upsertEntry(entry)

    suspend fun deleteEntry(entry: Entry) = db.getPasswordManagerDao().deleteEntry(entry)

    fun getAllEntries() = db.getPasswordManagerDao().getAllEntries()

    suspend fun upsertEntryDetail(entryDetail: EntryDetails) = db.getPasswordManagerDao().upsertEntryDetail(entryDetail)

    suspend fun deleteEntryDetail(entryDetail: EntryDetails) = db.getPasswordManagerDao().deleteEntryDetail(entryDetail)

    fun getAllEntryDetails() = db.getPasswordManagerDao().getAllEntryDetails()


}