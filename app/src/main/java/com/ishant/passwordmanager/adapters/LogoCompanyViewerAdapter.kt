package com.ishant.passwordmanager.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.LayoutViewAccountInfoBinding
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.security.EncryptionDecryption
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import com.ishant.passwordmanager.util.Passwords.Companion.PASSWORD1
import com.ishant.passwordmanager.util.Passwords.Companion.PASSWORD2


class LogoCompanyViewerAdapter(
    private val viewModel: CreateEditViewPasswordViewModel,
    private val owner: LifecycleOwner,
    private val mainView: View,
    private val mContext: Context,
    private val securityClass: EncryptionDecryption
): RecyclerView.Adapter<LogoCompanyViewerAdapter.LogoCompanyViewerAdapterViewHolder>() {
    inner class LogoCompanyViewerAdapterViewHolder(val binding: LayoutViewAccountInfoBinding): RecyclerView.ViewHolder(
        binding.root
    )

    private val differCallback = object: DiffUtil.ItemCallback<EntryDetail>() {
        override fun areItemsTheSame(oldItem: EntryDetail, newItem: EntryDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EntryDetail, newItem: EntryDetail): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LogoCompanyViewerAdapterViewHolder {
        val view = LayoutViewAccountInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LogoCompanyViewerAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: LogoCompanyViewerAdapterViewHolder, position: Int) {
        val entry = differ.currentList[position]

        viewModel.getAllEncryptedKeys(entry.id).observe(owner, Observer { encryptedKeyList ->
            val encryptedKey = encryptedKeyList[0]
            val decryptedData = securityClass.decrypt(
                entry.detailContent,
                encryptedKey.emdKey,
                securityClass.getKey()
            )

            holder.binding.tvInfoType.text = entry.detailType
            holder.binding.tvInfoContent.text = decryptedData
            setEntryDetailIcon(entry.detailType, holder.binding.ivInfoIcon)

            holder.binding.btnCopyInfo.setOnClickListener {

                val clipboard: ClipboardManager? = getSystemService(
                    mContext,
                    ClipboardManager::class.java
                )
                val clip = ClipData.newPlainText(entry.detailType, decryptedData)
                clipboard?.setPrimaryClip(clip)

                Snackbar.make(mainView, "Text copied", Snackbar.LENGTH_SHORT).show()
            }

            if (entry.detailType == "Password") {
                holder.binding.btnPasswordToggleInfo.visibility = View.VISIBLE

                var visiblePassword = false
                holder.binding.tvInfoContent.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                holder.binding.btnPasswordToggleInfo.setImageResource(R.drawable.ic_visibility_off)

                holder.binding.btnPasswordToggleInfo.setOnClickListener {
                    if (visiblePassword == false) {
                        holder.binding.tvInfoContent.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        holder.binding.btnPasswordToggleInfo.setImageResource(R.drawable.ic_visibility_on)
                        visiblePassword = true
                    } else {
                        holder.binding.tvInfoContent.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        holder.binding.btnPasswordToggleInfo.setImageResource(R.drawable.ic_visibility_off)
                        visiblePassword = false
                    }
                }
            }

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