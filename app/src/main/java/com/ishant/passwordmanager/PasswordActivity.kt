package com.ishant.passwordmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ishant.passwordmanager.databinding.ActivityMainBinding

class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

    }
}