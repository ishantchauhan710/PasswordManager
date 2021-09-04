package com.ishant.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.LayoutViewAccountInfoBinding
import com.ishant.passwordmanager.databinding.LogoCompanyBinding
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.util.CompanyList

class LogoCompanyViewerAdapter(): RecyclerView.Adapter<LogoCompanyViewerAdapter.LogoCompanyViewerAdapterViewHolder>() {
    inner class LogoCompanyViewerAdapterViewHolder(val binding: LayoutViewAccountInfoBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object: DiffUtil.ItemCallback<EntryDetail>() {
        override fun areItemsTheSame(oldItem: EntryDetail, newItem: EntryDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EntryDetail, newItem: EntryDetail): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LogoCompanyViewerAdapterViewHolder {
        val view = LayoutViewAccountInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LogoCompanyViewerAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: LogoCompanyViewerAdapterViewHolder, position: Int) {
        val entry = differ.currentList[position]

        holder.binding.tvInfoType.text = entry.detailType
        holder.binding.tvInfoContent.text = entry.detailContent
        setEntryDetailIcon(entry.detailType,holder.binding.ivInfoIcon)


    }

    fun setEntryDetailIcon(type: String, img: ImageView) {
        when(type) {
            "Username" -> img.setImageResource(R.drawable.ic_username_info)
            "Email" -> img.setImageResource(R.drawable.ic_mail_info)
            "Phone Number" -> img.setImageResource(R.drawable.ic_phone_info)
            "Password" -> img.setImageResource(R.drawable.ic_password_change_info)
            "Website" -> img.setImageResource(R.drawable.ic_website_info)
            "Notes" -> img.setImageResource(R.drawable.ic_note_info)
        }
    }



}