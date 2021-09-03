package com.ishant.passwordmanager.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetails

@Dao
interface PasswordManagerDao {
    @Insert
    suspend fun upsertEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("SELECT * FROM entry")
    fun getAllEntries(): LiveData<Entry>

    @Insert
    suspend fun upsertEntryDetail(entryDetail: EntryDetails)

    @Delete
    suspend fun deleteEntryDetail(entryDetail: EntryDetails)

    @Query("SELECT * FROM entry_details")
    fun getAllEntryDetails(): LiveData<EntryDetails>



}