package com.ishant.passwordmanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetails
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import kotlinx.coroutines.launch

class CreateEditViewPasswordViewModel(val repository: PasswordManagerRepository): ViewModel() {

    fun upsertEntry(entry: Entry) = viewModelScope.launch {
        repository.upsertEntry(entry)
    }

    fun deleteEntry(entry: Entry) = viewModelScope.launch {
        repository.deleteEntry(entry)
    }

    fun getAllEntries() = repository.getAllEntries()

    fun upsertEntryDetail(entryDetail: EntryDetails) = viewModelScope.launch {
        repository.upsertEntryDetail(entryDetail)
    }

    fun deleteEntryDetail(entryDetail: EntryDetails) = viewModelScope.launch {
        repository.deleteEntryDetail(entryDetail)
    }

    fun getAllEntryDetails() = repository.getAllEntryDetails()


}