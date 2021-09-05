package com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.LogoCompanyViewerAdapter
import com.ishant.passwordmanager.databinding.FragmentViewPasswordsBinding
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity


class ViewPasswordsFragment : Fragment(R.layout.fragment_view_passwords) {

    private lateinit var binding: FragmentViewPasswordsBinding

    private val args: ViewPasswordsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewPasswordsBinding.bind(view)

        val data = args.data

        val entryDetailList = mutableListOf<EntryDetail>()
        val viewModel = (activity as CreateEditViewPasswordActivity).viewModel

        val entryDetailAdapter = LogoCompanyViewerAdapter(viewModel,viewLifecycleOwner)
        binding.rvAccountDetails.apply {
            adapter = entryDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

            viewModel.getAllEntryDetails(data.toInt()).observe(viewLifecycleOwner, Observer {
                entryDetailAdapter.differ.submitList(it)
            })


    }
}