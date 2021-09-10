package com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.LogoCompanyViewerAdapter
import com.ishant.passwordmanager.databinding.FragmentViewPasswordsBinding
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.security.EncryptionDecryption
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity
import com.ishant.passwordmanager.util.CompanyListData
import kotlinx.android.synthetic.main.fragment_view_passwords.view.*


class ViewPasswordsFragment : Fragment(R.layout.fragment_view_passwords) {

    private lateinit var binding: FragmentViewPasswordsBinding

    private val args: ViewPasswordsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewPasswordsBinding.bind(view)

        val data = args.data

        val viewModel = (activity as CreateEditViewPasswordActivity).viewModel

        val iconId = data.icon

        val iconList = CompanyListData.companyListData
        for(icon in iconList) {
            if(icon.id == iconId) {
                binding.ivCompanyLogo.setImageResource(icon.companyIcon)
                break
            } else {
                binding.ivCompanyLogo.setImageResource(R.drawable.cl_general_account)
            }
        }


        binding.tvCompanyName.text = data.title
        binding.tvCategory.text = data.category
        when(data.category) {
            "Social" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_social)
            "Mails" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_mail)
            "Cards" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_card)
            "Work" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_work)
            "Other" -> binding.ivCategoryIcon.setImageResource(R.drawable.ic_others)
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(requireContext(), PasswordActivity::class.java)
            startActivity(intent)
            (activity as CreateEditViewPasswordActivity).finish()
        }



        val securityClass = EncryptionDecryption()
        val entryDetailAdapter = LogoCompanyViewerAdapter(viewModel,viewLifecycleOwner,view,requireContext(),securityClass)
        binding.rvAccountDetails.apply {
            adapter = entryDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        viewModel.getAllEntryDetails(data.id).observe(viewLifecycleOwner, Observer {
            entryDetailAdapter.differ.submitList(it)
        })

        /*
              val emptyAdapterList = listOf<EntryDetail>()
            val emptyAdapter = LogoCompanyViewerAdapter(viewModel,viewLifecycleOwner,view,requireContext(),securityClass)
            binding.rvAccountDetails.adapter = emptyAdapter
            emptyAdapter.differ.submitList(emptyAdapterList)

         */




    }
}