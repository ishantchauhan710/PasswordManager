package com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.ActivityCreateEditViewPasswordBinding
import com.ishant.passwordmanager.databinding.ActivityPasswordBinding
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity
import com.ishant.passwordmanager.ui.factories.CreateEditViewPasswordViewModelProviderFactory
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel

class CreateEditViewPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateEditViewPasswordBinding
    lateinit var viewModel: CreateEditViewPasswordViewModel

    lateinit var command: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        val database = PasswordManagerDatabase(this)
        val repository = PasswordManagerRepository(database)
        val factory = CreateEditViewPasswordViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(CreateEditViewPasswordViewModel::class.java)

        binding = ActivityCreateEditViewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        command = intent?.getStringExtra("command").toString()
        val data = intent?.getSerializableExtra("data")



        if(command=="view") {
            val bundle = Bundle().apply {
                putSerializable("data",data)
            }
            findNavController(R.id.fragment2).popBackStack()
            findNavController(R.id.fragment2).navigate(R.id.viewPasswordsFragment,bundle)
        }

        if(command=="edit") {
            val bundle = Bundle().apply {
                putSerializable("data",data)
            }
            findNavController(R.id.fragment2).popBackStack()
            findNavController(R.id.fragment2).navigate(R.id.editPasswordFragment,bundle)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, PasswordActivity::class.java)
        startActivity(intent)
        finish()
    }

}