package com.ishant.passwordmanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishant.passwordmanager.db.entities.EncryptedKey
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import kotlinx.coroutines.launch

class CreateEditViewPasswordViewModel(private val repository: PasswordManagerRepository): ViewModel() {

    suspend fun upsertEntry(entry: Entry): Long = repository.upsertEntry(entry)

    fun deleteEntry(entry: Entry) = viewModelScope.launch {
        repository.deleteEntry(entry)
    }

    fun getAllEntries() = repository.getAllEntries()

    suspend fun upsertEntryDetail(entryDetail: EntryDetail) = repository.upsertEntryDetail(entryDetail)


    fun deleteEntryDetail(entryDetail: EntryDetail) = viewModelScope.launch {
        repository.deleteEntryDetail(entryDetail)
    }

    fun getAllEntryDetails(id: Int) = repository.getAllEntryDetails(id)

    fun upsertEncryptedKey(encryptedKey: EncryptedKey) = viewModelScope.launch {
        repository.upsertEncryptedKey(encryptedKey)
    }

    fun deleteEncryptedKey(encryptedKey: EncryptedKey) = viewModelScope.launch {
        repository.deleteEncryptedKey(encryptedKey)
    }


    fun getAllEncryptedKeys(id: Int) = repository.getAllEncryptedKey(id)



}