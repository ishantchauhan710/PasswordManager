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

        // Variable Names
        // emd: Encrypt Main Data
        // eed: Encrypt Encrypted Data
        // This is the main data of user that will be encrypted. It will include emails, passwords, usernames etc
        val dataToEncrypt = "This is data to Encrypt"
        // This is a password to generate key
        val emdPassword = "ishant"
        // A random salt is generated which will be used in creating key
        val emdSalt = saltString(generateSalt())
        // A key is generated using the password and salt
        val emdKey = generateKeyFromPassword(emdPassword,emdSalt)
        // Our main data will get encrypted using the key we generated
        val emdEncryptedString = encrypt(dataToEncrypt, emdKey).toString()
        // Now we will again encrypt this encrypted string
        // This is a new password to create a new key to encrypt the encrypted string
        val eedPassword = "chauhan"
        // Another salt generated to generate new key to encrypt the encrypted string
        val eedSalt = saltString(generateSalt())
        // New key is generated using new password and new salt
        val eedKey = generateKeyFromPassword(eedPassword,eedSalt)
        // Now here we will encrypt the encrypted string
        val eedEncryptedString = encrypt(emdEncryptedString, eedKey).toString()
        // Now we need to decrypt the data
        // This will return the first level encrypted string of our main data
        val eedDecryptedString = decryptString(CipherTextIvMac(eedEncryptedString),eedKey)
        val emdDecryptedString = decryptString(CipherTextIvMac(emdEncryptedString),emdKey)
        val finalDecryptedString = emdDecryptedString
        Log.e("IshantEncryption","Password 1 (EMD): $emdPassword\n")
        Log.e("IshantEncryption","Password 2 (EED): $eedPassword\n")
        Log.e("IshantEncryption","Data to Encrypt: $dataToEncrypt\n")
        Log.e("IshantEncryption","Key1 (EMD): $emdKey\n")
        Log.e("IshantEncryption","Key2 (EED): $eedKey\n")
        Log.e("IshantEncryption","Encrypted Data String1 (EMD): $emdEncryptedString\n")
        Log.e("IshantEncryption","Encrypted Data String2 (EED): $eedEncryptedString\n")
        Log.e("IshantEncryption","Main Data: $dataToEncrypt\n")
        Log.e("IshantEncryption","Final Decrypted String: $finalDecryptedString\n")















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