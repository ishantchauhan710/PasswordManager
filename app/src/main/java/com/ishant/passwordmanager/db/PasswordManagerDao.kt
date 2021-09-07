package com.ishant.passwordmanager.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ishant.passwordmanager.db.entities.EncryptedKey
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

    @Query("SELECT * FROM entry WHERE favourite = 1")
    fun getAllFavouriteEntries(): LiveData<List<Entry>>

    @Query("UPDATE entry SET favourite = :isFavourite WHERE id = :id")
    suspend fun setFavouriteEntry(isFavourite: Int, id: Int)

    @Query("SELECT * FROM entry WHERE title LIKE '%' || :text || '%'")
    fun searchEntries(text: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry WHERE category = :category")
    fun sortEntries(category: String): LiveData<List<Entry>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEntryDetail(entryDetail: EntryDetail): Long

    @Query("DELETE FROM entry_details WHERE entryId = :id")
    suspend fun deleteEntryDetails(id: Int)

    @Query("SELECT * FROM entry_details WHERE entryId = :id")
    fun getAllEntryDetails(id: Int): LiveData<List<EntryDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertKey(encryptedKey: EncryptedKey)

    @Query("DELETE FROM keys WHERE entryDetailId = :id")
    suspend fun deleteKeys(id: Int)

    @Query("SELECT * FROM keys WHERE entryDetailId = :id")
    fun getAllEncryptedKeys(id: Int): LiveData<List<EncryptedKey>>


}