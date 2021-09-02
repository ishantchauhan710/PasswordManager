package com.ishant.passwordmanager.ui.activities.password_activity.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.PasswordAdapter
import com.ishant.passwordmanager.databinding.FragmentPasswordsBinding

class PasswordsFragment : Fragment(R.layout.fragment_passwords) {

    private lateinit var binding: FragmentPasswordsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordsBinding.bind(view)

        val adapter = PasswordAdapter(requireContext())

        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())

    }

}