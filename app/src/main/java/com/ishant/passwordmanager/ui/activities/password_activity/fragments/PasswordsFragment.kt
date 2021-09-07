package com.ishant.passwordmanager.ui.activities.password_activity.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.PasswordAdapter
import com.ishant.passwordmanager.databinding.FragmentPasswordsBinding
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import kotlinx.coroutines.*

class PasswordsFragment : Fragment(R.layout.fragment_passwords) {

    private lateinit var binding: FragmentPasswordsBinding
    lateinit var viewModel: CreateEditViewPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as PasswordActivity).viewModel


        binding = FragmentPasswordsBinding.bind(view)


        val adapter = PasswordAdapter(requireContext(),viewModel,viewLifecycleOwner,view,(activity as PasswordActivity))

        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())

        viewModel.sortedList.observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })

        adapter.setOnItemClickListener {

            val command = "view"
            val intent = Intent(requireContext(),CreateEditViewPasswordActivity::class.java)
            intent.putExtra("command",command)
            intent.putExtra("data",it)
            //Toast.makeText(requireContext(),"Id: ${it.id}",Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }


    }



}