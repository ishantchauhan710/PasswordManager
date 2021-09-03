package com.ishant.passwordmanager.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ishant.passwordmanager.repository.PasswordManagerRepository

class CreateEditViewPasswordViewModelProviderFactory(val repository: PasswordManagerRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return repository as T
    }
}