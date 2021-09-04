package com.ishant.passwordmanager.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ishant.passwordmanager.db.entities.EncryptedSalt
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetail

@Dao
interface PasswordManagerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEntry(entry: Entry): Long

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("SELECT * FROM entry")
    fun getAllEntries(): LiveData<List<Entry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEntryDetail(entryDetail: EntryDetail): Long

    @Delete
    suspend fun deleteEntryDetail(entryDetail: EntryDetail)

    @Query("SELECT * FROM entry_details")
    fun getAllEntryDetails(): LiveData<List<EntryDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSalt(encryptedSalt: EncryptedSalt)

    @Delete
    suspend fun deleteSalt(encryptedSalt: EncryptedSalt)

    @Query("SELECT * FROM salts")
    fun getAllEncryptedSalts(): LiveData<List<EncryptedSalt>>







}