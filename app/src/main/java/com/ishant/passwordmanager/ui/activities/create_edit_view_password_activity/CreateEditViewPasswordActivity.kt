package com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.ActivityCreateEditViewPasswordBinding
import com.ishant.passwordmanager.databinding.ActivityPasswordBinding
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.ui.factories.CreateEditViewPasswordViewModelProviderFactory
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel

class CreateEditViewPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateEditViewPasswordBinding
    lateinit var viewModel: CreateEditViewPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val database = PasswordManagerDatabase(this)
        val repository = PasswordManagerRepository(database)
        val factory = CreateEditViewPasswordViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(CreateEditViewPasswordViewModel::class.java)

        binding = ActivityCreateEditViewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()





    }
}