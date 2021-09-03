package com.ishant.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.LayoutAccountInfoBinding
import com.ishant.passwordmanager.db.entities.EntryDetails

class PasswordAccountInfoAdapter(private val accountDetails: MutableList<EntryDetails>): RecyclerView.Adapter<PasswordAccountInfoAdapter.PasswordAccountInfoAdapterViewHolder>() {
    inner class PasswordAccountInfoAdapterViewHolder(val binding: LayoutAccountInfoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PasswordAccountInfoAdapterViewHolder {
        val view = LayoutAccountInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PasswordAccountInfoAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return accountDetails.size
    }

    override fun onBindViewHolder(holder: PasswordAccountInfoAdapterViewHolder, position: Int) {
        val detail = accountDetails[position]
        holder.binding.tvInfoType.text = detail.detailType
        holder.binding.tvInfoContent.text = detail.detailContent

        when(holder.binding.tvInfoType.text) {
            "Username" -> holder.binding.ivInfoIcon.setImageResource(R.drawable.ic_username_info)
            "Email" -> holder.binding.ivInfoIcon.setImageResource(R.drawable.ic_mail_info)
            "Phone Number" -> holder.binding.ivInfoIcon.setImageResource(R.drawable.ic_phone_info)
            "Password" -> holder.binding.ivInfoIcon.setImageResource(R.drawable.ic_password_change_info)
            "Website" -> holder.binding.ivInfoIcon.setImageResource(R.drawable.ic_website_info)
            "Notes" -> holder.binding.ivInfoIcon.setImageResource(R.drawable.ic_note_info)
        }

        holder.binding.btnDeleteInfo.setOnClickListener {
            accountDetails.removeAt(holder.adapterPosition)
            notifyDataSetChanged()
        }

    }
}