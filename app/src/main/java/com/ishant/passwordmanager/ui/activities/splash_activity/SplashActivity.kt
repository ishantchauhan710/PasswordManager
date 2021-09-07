package com.ishant.passwordmanager.ui.activities.splash_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.ui.activities.lock_activity.LockActivity
import com.ishant.passwordmanager.ui.factories.CreateEditViewPasswordViewModelProviderFactory
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val database = PasswordManagerDatabase(this)
        val repository = PasswordManagerRepository(database)
        val factory = CreateEditViewPasswordViewModelProviderFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(CreateEditViewPasswordViewModel::class.java)

        viewModel.getLockPassword().observe(this, Observer {
            if(it.size==0) {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(1000)
                    val intent = Intent(this@SplashActivity,LockActivity::class.java)
                    intent.putExtra("command","createpassword")
                    startActivity(intent)
                    finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val intent = Intent(this@SplashActivity, LockActivity::class.java)
                    intent.putExtra("command", "askforpassword")
                    startActivity(intent)
                    finish()
                }
            }
        })

    }
}