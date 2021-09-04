package com.ishant.passwordmanager.ui.activities.password_activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.ActivityPasswordBinding
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity
import com.ishant.passwordmanager.ui.factories.CreateEditViewPasswordViewModelProviderFactory
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import com.tozny.crypto.android.AesCbcWithIntegrity.*


class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var viewModel: CreateEditViewPasswordViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PasswordManagerDatabase(this)
        val repository = PasswordManagerRepository(database)
        val factory = CreateEditViewPasswordViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CreateEditViewPasswordViewModel::class.java)


        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()
        setUpNavigationBar()


        val navController = Navigation.findNavController(this, R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.btnNewPassword.setOnClickListener {
            val intent = Intent(this, CreateEditViewPasswordActivity::class.java)
            startActivity(intent)
        }


        val password = "ishant"
        val dataToEncrypt = "This is data to Encrypt"

        val salt = saltString(generateSalt())
        val key = generateKeyFromPassword(password, salt)
        val encryptedDataString = encrypt(dataToEncrypt, key).toString()

        val encryptedData = CipherTextIvMac(encryptedDataString)
        val decryptedData = decryptString(encryptedData, key)

        Log.e("IshantEncryption", "Password: $password\n")
        Log.e("IshantEncryption", "Data To Encrypt: $dataToEncrypt\n")
        Log.e("IshantEncryption", "Salt: $salt\n")
        Log.e("IshantEncryption", "Key: $key\n")
        Log.e("IshantEncryption", "Encrypted Data String: $encryptedDataString\n")
        Log.e("IshantEncryption", "Encrypted Data: $encryptedData\n")
        Log.e("IshantEncryption", "Decrypted Data: $decryptedData\n")













    }


    private fun setUpActionBar() {
        // Setting up Action Bar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Action Bar Toggle Color
        val upArrow: Drawable = resources.getDrawable(R.drawable.ic_drawable_toggle)
        upArrow.setColorFilter(Color.parseColor("#343434"), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

    private fun setUpNavigationBar() {
        // Navigation Bar Color
        window.navigationBarColor = Color.BLACK
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miSocial -> Toast.makeText(applicationContext, "Social", Toast.LENGTH_SHORT)
                    .show()
                R.id.miChangePassword -> Toast.makeText(
                    applicationContext,
                    "Change Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menu = menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.miSearchButton -> Toast.makeText(
                applicationContext,
                "Menu Search Button Pressed",
                Toast.LENGTH_SHORT
            ).show()
        }

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}