package com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ishant.passwordmanager.R

class CreateEditViewPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_view_password)
        supportActionBar?.hide()



    }
}