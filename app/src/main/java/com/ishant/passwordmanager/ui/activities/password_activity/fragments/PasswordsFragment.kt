package com.ishant.passwordmanager.ui.activities.password_activity.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        binding = FragmentPasswordsBinding.bind(view)

        val adapter = PasswordAdapter(requireContext())

        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())

        viewModel = (activity as PasswordActivity).viewModel


        viewModel.getAllEntries().observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })

        adapter.setOnItemClickListener {
            val command = "view"
            val intent = Intent(requireContext(),CreateEditViewPasswordActivity::class.java)
            intent.putExtra("command",command)
            startActivity(intent)
        }


    }

}