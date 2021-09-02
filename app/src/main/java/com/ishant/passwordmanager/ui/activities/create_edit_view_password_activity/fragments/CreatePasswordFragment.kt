package com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.PasswordAccountInfoAdapter
import com.ishant.passwordmanager.databinding.FragmentCreatePasswordBinding
import com.ishant.passwordmanager.databinding.FragmentPasswordsBinding


class CreatePasswordFragment : Fragment(R.layout.fragment_create_password) {

    private lateinit var binding: FragmentCreatePasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePasswordBinding.bind(view)

        val rvAccountDetails = binding.rvAccountDetails
        val adapter = PasswordAccountInfoAdapter()
        rvAccountDetails.adapter = adapter
        rvAccountDetails.layoutManager = LinearLayoutManager(requireContext())


    }

}