package com.ishant.passwordmanager.ui.activities.password_activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.PasswordAdapter
import com.ishant.passwordmanager.databinding.ActivityPasswordBinding
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity
import com.ishant.passwordmanager.ui.activities.password_activity.fragments.SearchPasswordFragment
import com.ishant.passwordmanager.ui.factories.CreateEditViewPasswordViewModelProviderFactory
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import com.tozny.crypto.android.AesCbcWithIntegrity.*
import java.security.KeyStore


class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var viewModel: CreateEditViewPasswordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PasswordManagerDatabase(this)
        val repository = PasswordManagerRepository(database)
        val factory = CreateEditViewPasswordViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, factory).get(CreateEditViewPasswordViewModel::class.java)


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

        findNavController(R.id.fragment).addOnDestinationChangedListener { controller, destination, arguments ->

            when(destination.id) {
                R.id.passwordsFragment -> {
                    binding.toolbar.title = "Passwords"
                }
                R.id.searchPasswordFragment -> {
                    binding.toolbar.title = "Search"
                }
                R.id.favouritePasswordsFragment -> {
                    binding.toolbar.title = "Favourites"
                }
                R.id.generatePasswordFragment -> {
                    binding.toolbar.title = "Generate Password"
                }

            }

            if(destination.id != R.id.searchPasswordFragment) {
                binding.toolbar.collapseActionView()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menu = menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.miSearchButton -> {

                findNavController(R.id.fragment).navigate(R.id.searchPasswordFragment)



                val myActionMenuItem: MenuItem = item

                val searchView = myActionMenuItem.actionView as androidx.appcompat.widget.SearchView

                val v: View = searchView.findViewById(R.id.search_plate)
                v.setBackgroundColor(Color.parseColor("#ffffff"))

                searchView.queryHint = "Search..."

                val searchText = searchView.findViewById(R.id.search_src_text) as TextView
                searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)


                if (toggle.onOptionsItemSelected(item)) {
                    return true
                }

                    val searchCloseIcon: ImageView =
                        searchView.findViewById(R.id.search_close_btn) as ImageView
                    searchCloseIcon.setImageResource(R.drawable.ic_clear)



                    searchView.setOnQueryTextListener(object :
                        androidx.appcompat.widget.SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String?): Boolean {

                            return false
                        }


                        override fun onQueryTextChange(newText: String?): Boolean {

                                val emptyList = emptyList<Entry>()

                                if(newText!=null && newText!="") {
                                    viewModel.searchEntries(newText)
                                        .observe(this@PasswordActivity, Observer {
                                            viewModel.filteredSearchList.postValue(it)
                                        })
                                } else {
                                    viewModel.filteredSearchList.postValue(emptyList)
                                }

                            return false
                        }
                    })
                }
            }
            return true

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
            when (it.itemId) {
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

}
