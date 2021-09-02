package com.ishant.passwordmanager

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.ishant.passwordmanager.databinding.ActivityMainBinding

class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,binding.toolbar,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Action Bar Color
        val upArrow: Drawable = resources.getDrawable(R.drawable.ic_drawable_toggle)
        upArrow.setColorFilter(Color.parseColor("#343434"), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        // Navigation Bar Color
        window.navigationBarColor = Color.BLACK

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miItem1 -> Toast.makeText(applicationContext,"Item 1",Toast.LENGTH_SHORT).show()
                R.id.miItem2 -> Toast.makeText(applicationContext,"Item 2",Toast.LENGTH_SHORT).show()
                R.id.miItem3 -> Toast.makeText(applicationContext,"Item 3",Toast.LENGTH_SHORT).show()
            }
            true
        }

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}