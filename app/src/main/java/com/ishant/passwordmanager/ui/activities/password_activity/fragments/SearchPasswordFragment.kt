package com.ishant.passwordmanager.ui.activities.password_activity.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.PasswordAdapter
import com.ishant.passwordmanager.databinding.FragmentSearchPasswordBinding
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity

class SearchPasswordFragment : Fragment(R.layout.fragment_search_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (activity as PasswordActivity).viewModel
        val binding = FragmentSearchPasswordBinding.bind(view)

        val emptyList = listOf<Entry>()


        val adapter = PasswordAdapter(requireContext(),viewModel,viewLifecycleOwner,view,(activity as PasswordActivity))


        binding.rvSearchEntries.adapter = adapter
        binding.rvSearchEntries.layoutManager = LinearLayoutManager(requireContext())

        viewModel.filteredSearchList.observe(viewLifecycleOwner, Observer {

            if(it.isNullOrEmpty()) {
                adapter.differ.submitList(emptyList)
                binding.tvEmptySearch.visibility = View.VISIBLE
            } else {
                binding.tvEmptySearch.visibility = View.GONE
                adapter.differ.submitList(it)
            }

        })

        adapter.setOnItemClickListener {

            val command = "view"
            val intent = Intent(requireContext(), CreateEditViewPasswordActivity::class.java)
            intent.putExtra("command",command)
            intent.putExtra("data",it)
            //Toast.makeText(requireContext(),"Id: ${it.id}",Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }

    }




}