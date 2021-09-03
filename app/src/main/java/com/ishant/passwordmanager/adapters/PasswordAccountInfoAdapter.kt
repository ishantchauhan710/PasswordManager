package com.ishant.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ishant.passwordmanager.databinding.LayoutAccountInfoBinding
import com.ishant.passwordmanager.db.entities.AccountDetails

class PasswordAccountInfoAdapter: RecyclerView.Adapter<PasswordAccountInfoAdapter.PasswordAccountInfoAdapterViewHolder>() {
    inner class PasswordAccountInfoAdapterViewHolder(val binding: LayoutAccountInfoBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object: DiffUtil.ItemCallback<AccountDetails>() {
        override fun areItemsTheSame(oldItem: AccountDetails, newItem: AccountDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountDetails, newItem: AccountDetails): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PasswordAccountInfoAdapterViewHolder {
        val view = LayoutAccountInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PasswordAccountInfoAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: PasswordAccountInfoAdapterViewHolder, position: Int) {

    }
}