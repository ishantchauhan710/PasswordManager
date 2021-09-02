package com.ishant.passwordmanager.ui.activities.password_activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.ActivityPasswordBinding
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity

class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordBinding
    private lateinit var toggle: ActionBarDrawerToggle



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()
        setUpNavigationBar()

        val navController = Navigation.findNavController(this, R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.btnNewPassword.setOnClickListener {
            val intent = Intent(this,CreateEditViewPasswordActivity::class.java)
            startActivity(intent)
        }

    }


    private fun setUpActionBar() {
        // Setting up Action Bar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,binding.toolbar, R.string.open, R.string.close)
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
                R.id.miSocial -> Toast.makeText(applicationContext,"Social",Toast.LENGTH_SHORT).show()
                R.id.miChangePassword -> Toast.makeText(applicationContext,"Change Password",Toast.LENGTH_SHORT).show()
            }
            true
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menu = menuInflater.inflate(R.menu.action_bar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.miSearchButton -> Toast.makeText(applicationContext,"Menu Search Button Pressed",Toast.LENGTH_SHORT).show()
        }

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}