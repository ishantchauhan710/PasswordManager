package com.ishant.passwordmanager.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishant.passwordmanager.db.entities.EncryptedKey
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import kotlinx.coroutines.launch

class CreateEditViewPasswordViewModel(private val repository: PasswordManagerRepository): ViewModel() {

    val filteredSearchList: MutableLiveData<List<Entry>> = MutableLiveData<List<Entry>>()

    suspend fun upsertEntry(entry: Entry): Long = repository.upsertEntry(entry)

    fun deleteEntry(entry: Entry) = viewModelScope.launch {
        repository.deleteEntry(entry)
    }

    fun getAllEntries() = repository.getAllEntries()

    fun getAllFavouriteEntries() = repository.getAllFavouriteEntries()

    suspend fun setFavouriteEntry(isFavourite: Int, id: Int) = viewModelScope.launch {
        repository.setFavouriteEntry(isFavourite,id)
    }

    fun searchEntries(text: String) = repository.searchEntries(text)


    suspend fun upsertEntryDetail(entryDetail: EntryDetail) = repository.upsertEntryDetail(entryDetail)


    fun deleteEntryDetails(id: Int) = viewModelScope.launch {
        repository.deleteEntryDetails(id)
    }

    fun getAllEntryDetails(id: Int) = repository.getAllEntryDetails(id)

    fun upsertEncryptedKey(encryptedKey: EncryptedKey) = viewModelScope.launch {
        repository.upsertEncryptedKey(encryptedKey)
    }

    fun deleteEncryptedKeys(id: Int) = viewModelScope.launch {
        repository.deleteEncryptedKeys(id)
    }


    fun getAllEncryptedKeys(id: Int) = repository.getAllEncryptedKey(id)



}