package com.ishant.passwordmanager.ui.activities.password_activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.ActivityPasswordBinding
import com.ishant.passwordmanager.db.PasswordManagerDatabase
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.repository.PasswordManagerRepository
import com.ishant.passwordmanager.security.EncryptionDecryption
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity
import com.ishant.passwordmanager.ui.activities.lock_activity.LockActivity
import com.ishant.passwordmanager.ui.factories.CreateEditViewPasswordViewModelProviderFactory
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import com.mikepenz.materialdrawer.holder.*
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.iconDrawable
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.util.updateItem
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.tozny.crypto.android.AesCbcWithIntegrity.*
import kotlinx.android.synthetic.main.activity_password.view.*


class PasswordActivity : AppCompatActivity() {




    lateinit var binding: ActivityPasswordBinding
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

        //Toast.makeText(this, EncryptionDecryption().getKey(),Toast.LENGTH_LONG).show()

        val emptyList = listOf<Entry>()
        viewModel.filteredSearchList.postValue(emptyList)

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


        // Header Layout
        AccountHeaderView(this).apply {

            val drawerHeaderLayout = LayoutInflater.from(this@PasswordActivity).inflate(
                R.layout.drawer_header_layout,
                null,
                false
            )
            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            addView(drawerHeaderLayout, params)
            attachToSliderView(binding.navView)
           /* attachToSliderView(binding.navView) // attach to the slider
            headerBackground =  ImageHolder(R.drawable.logo)
            headerBackgroundScaleType = ImageView.ScaleType.FIT_CENTER
            onAccountHeaderListener = { view, profile, current ->
                // react to profile changes
                false
            }
            withSavedInstance(savedInstanceState)*/
        }




        //if you want to update the items at a later time it is recommended to keep it in a variable
        val item1 = PrimaryDrawerItem().apply {
            nameRes = R.string.all
            identifier = 1
            iconDrawable = resources.getDrawable(R.drawable.ic_all)

            badgeStyle = BadgeStyle().apply {
                textColor = ColorHolder.fromColor(Color.WHITE)
                color = ColorHolder.fromColor(Color.parseColor("#EF5350"))
                corners = DimenHolder.fromDp(50)
            }
        }

        val item2 = PrimaryDrawerItem().apply {
            nameRes = R.string.social
            identifier = 2
            iconDrawable = resources.getDrawable(R.drawable.ic_social)

            badgeStyle = BadgeStyle().apply {
                textColor = ColorHolder.fromColor(Color.WHITE)
                color = ColorHolder.fromColor(Color.parseColor("#3D5AFE"))
                corners = DimenHolder.fromDp(50)
            }
        }

        val item3 = PrimaryDrawerItem().apply {
            nameRes = R.string.mails
            identifier = 3
            iconDrawable = resources.getDrawable(R.drawable.ic_mail)

            badgeStyle = BadgeStyle().apply {
                textColor = ColorHolder.fromColor(Color.WHITE)
                color = ColorHolder.fromColor(Color.parseColor("#8E24AA"))
                corners = DimenHolder.fromDp(50)
            }
        }

        val item4 = PrimaryDrawerItem().apply {
            nameRes = R.string.card
            identifier = 4
            iconDrawable = resources.getDrawable(R.drawable.ic_card)

            badgeStyle = BadgeStyle().apply {
                textColor = ColorHolder.fromColor(Color.WHITE)
                color = ColorHolder.fromColor(Color.parseColor("#29B6F6"))
                corners = DimenHolder.fromDp(50)
            }
        }

        val item5 = PrimaryDrawerItem().apply {
            nameRes = R.string.work
            identifier = 5
            iconDrawable = resources.getDrawable(R.drawable.ic_work)

            badgeStyle = BadgeStyle().apply {
                textColor = ColorHolder.fromColor(Color.WHITE)
                color = ColorHolder.fromColor(Color.parseColor("#7CB342"))
                corners = DimenHolder.fromDp(50)
            }
        }

        val item6 = PrimaryDrawerItem().apply {
            nameRes = R.string.others
            identifier = 6
            iconDrawable = resources.getDrawable(R.drawable.ic_others)

            badgeStyle = BadgeStyle().apply {
                textColor = ColorHolder.fromColor(Color.WHITE)
                color = ColorHolder.fromColor(Color.parseColor("#FFA100"))
                corners = DimenHolder.fromDp(50)
            }
        }

        val item7 = PrimaryDrawerItem().apply {
            nameRes = R.string.changepassword
            identifier = 7
            iconDrawable = resources.getDrawable(R.drawable.ic_password_change)
            isSelectable = false
        }

        val item8 = PrimaryDrawerItem().apply {
            nameRes = R.string.exit
            identifier = 8
            iconDrawable = resources.getDrawable(R.drawable.ic_exit)
            isSelectable = false
        }

        viewModel.getAllEntries().observe(this, Observer {
            viewModel.sortedList.postValue(it)
            if (it.isNotEmpty()) {
                item1.apply {
                    badge = StringHolder("${it.size}")
                    binding.navView.updateItem(item1)
                }
            }
        })


        viewModel.sortEntries("Social").observe(this, Observer {
            if (it.isNotEmpty()) {
                item2.apply {
                    badge = StringHolder("${it.size}")
                    binding.navView.updateItem(item2)
                }
            }
        })

        viewModel.sortEntries("Mails").observe(this, Observer {
            if (it.isNotEmpty()) {
                item3.apply {
                    badge = StringHolder("${it.size}")
                    binding.navView.updateItem(item3)
                }
            }
        })

        viewModel.sortEntries("Cards").observe(this, Observer {
            if (it.isNotEmpty()) {
                item4.apply {
                    badge = StringHolder("${it.size}")
                    binding.navView.updateItem(item4)
                }
            }
        })

        viewModel.sortEntries("Work").observe(this, Observer {
            if (it.isNotEmpty()) {
                item5.apply {
                    badge = StringHolder("${it.size}")
                    binding.navView.updateItem(item5)
                }
            }
        })

        viewModel.sortEntries("Other").observe(this, Observer {
            if (it.isNotEmpty()) {
                item6.apply {
                    badge = StringHolder("${it.size}")
                    binding.navView.updateItem(item6)
                }
            }
        })



        // get the reference to the slider and add the items
        binding.navView.itemAdapter.add(
            item1,
            item2,
            item3,
            item4,
            item5,
            item6,
            DividerDrawerItem(),
            item7,
            item8
        )

        binding.navView.setSelection(1)

        // specify a click listener
        binding.navView.onDrawerItemClickListener = { v, drawerItem, position ->
            when(position) {
                1 -> {
                    viewModel.getAllEntries().observe(this, Observer {
                        viewModel.sortedList.postValue(it)
                        item1.apply {
                            badge = StringHolder("${it.size}")
                        }
                        binding.navView.updateItem(item1)
                    })
                }
                2 -> {
                    viewModel.sortEntries("Social").observe(this, Observer {
                        viewModel.sortedList.postValue(it)
                    })
                }
                3 -> {
                    viewModel.sortEntries("Mails").observe(this, Observer {
                        viewModel.sortedList.postValue(it)
                    })
                }
                4 -> {
                    viewModel.sortEntries("Cards").observe(this, Observer {
                        viewModel.sortedList.postValue(it)
                    })
                }
                5 -> {
                    viewModel.sortEntries("Work").observe(this, Observer {
                        viewModel.sortedList.postValue(it)
                    })
                }
                6 -> {
                    viewModel.sortEntries("Other").observe(this, Observer {
                        viewModel.sortedList.postValue(it)
                    })
                }

                8 -> {
                    val intent = Intent(this, LockActivity::class.java)
                    intent.putExtra("command", "changepassword")
                    startActivity(intent)
                }

                9 -> {
                    // Closes the app and removes it from android tasks on device
                    finishAndRemoveTask()
                }

            }

            false
        }


    }

    override fun onPause() {
        super.onPause()
        binding.navView.setSelection(1, true)
    }

    override fun onResume() {
        super.onResume()
        binding.navView.setSelection(1, true)
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

                        if (newText?.isNotEmpty() == true || newText?.isNotBlank() == true) {
                            viewModel.searchEntries(newText)
                                .observe(this@PasswordActivity, Observer {
                                    viewModel.filteredSearchList.postValue(it)
                                })
                        } else {
                            val emptyList = listOf<Entry>()
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

        /*
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

*/
    }

}
