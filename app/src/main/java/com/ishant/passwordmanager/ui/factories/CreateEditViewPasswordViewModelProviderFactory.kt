package com.ishant.passwordmanager.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel

class CreateEditViewPasswordViewModelProviderFactory(val repository: PasswordManagerRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateEditViewPasswordViewModel(repository) as T
    }
}