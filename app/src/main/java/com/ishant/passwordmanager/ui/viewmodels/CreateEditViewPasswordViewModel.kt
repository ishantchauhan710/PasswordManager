package com.ishant.passwordmanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishant.passwordmanager.db.entities.EncryptedSalt
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

    fun getAllEntryDetails() = repository.getAllEntryDetails()

    fun upsertEncryptedSalt(encryptedSalt: EncryptedSalt) = viewModelScope.launch {
        repository.upsertEncryptedSalt(encryptedSalt)
    }

    fun deleteEncryptedSalt(encryptedSalt: EncryptedSalt) = viewModelScope.launch {
        repository.deleteEncryptedSalt(encryptedSalt)
    }


    fun getAllEncryptedSalts() = repository.getAllEncryptedSalts()



}