package com.ishant.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.LayoutViewAccountInfoBinding
import com.ishant.passwordmanager.databinding.LogoCompanyBinding
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.security.EncryptionDecryption.Companion.decrypt
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import com.ishant.passwordmanager.util.CompanyList
import com.ishant.passwordmanager.util.EncryptedObject
import com.ishant.passwordmanager.util.Passwords.Companion.PASSWORD1
import com.ishant.passwordmanager.util.Passwords.Companion.PASSWORD2
import java.security.acl.Owner

class LogoCompanyViewerAdapter(private val viewModel: CreateEditViewPasswordViewModel, private val owner: LifecycleOwner): RecyclerView.Adapter<LogoCompanyViewerAdapter.LogoCompanyViewerAdapterViewHolder>() {
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

        viewModel.getAllEncryptedKeys(entry.id).observe(owner, Observer { encryptedSaltList ->
            val encryptedSalt = encryptedSaltList[0]
            val decryptedData =  decrypt(entry.detailContent,PASSWORD1,PASSWORD2,encryptedSalt.emdSalt,encryptedSalt.eedSalt)

            holder.binding.tvInfoType.text = entry.detailType
            holder.binding.tvInfoContent.text = decryptedData
            setEntryDetailIcon(entry.detailType,holder.binding.ivInfoIcon)
        })


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