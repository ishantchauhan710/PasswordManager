package com.ishant.passwordmanager.ui.activities.lock_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.ActivityLockBinding
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.ui.factories.CreateEditViewPasswordViewModelProviderFactory
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel

class LockActivity : AppCompatActivity() {

    lateinit var viewModel: CreateEditViewPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PasswordManagerDatabase(this)
        val repository = PasswordManagerRepository(database)
        val factory = CreateEditViewPasswordViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CreateEditViewPasswordViewModel::class.java)


        val binding = ActivityLockBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val command = intent.getStringExtra("command")

        if(command=="createpassword") {
            findNavController(R.id.fragment3).popBackStack()
            findNavController(R.id.fragment3).navigate(R.id.createLockFragment)
        }

        if(command=="askforpassword") {
            findNavController(R.id.fragment3).popBackStack()
            findNavController(R.id.fragment3).navigate(R.id.lockPasswordFragment)
        }


    }
}