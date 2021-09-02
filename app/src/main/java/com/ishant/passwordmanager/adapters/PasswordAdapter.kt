package com.ishant.passwordmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.PasswordBinding
import com.ishant.passwordmanager.db.entities.Password

class PasswordAdapter(val mContext: Context): RecyclerView.Adapter<PasswordAdapter.PasswordAdapterViewHolder>() {
    inner class PasswordAdapterViewHolder(val binding: PasswordBinding): RecyclerView.ViewHolder(binding.root)


    private val differCallback = object: DiffUtil.ItemCallback<Password>() {
        override fun areItemsTheSame(oldItem: Password, newItem: Password): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Password, newItem: Password): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordAdapterViewHolder {
        val view = PasswordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PasswordAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: PasswordAdapterViewHolder, position: Int) {

        holder.binding.ivOptions.setOnClickListener {
            val popupMenu = PopupMenu(mContext,it)
            popupMenu.menuInflater.inflate(R.menu.options_menu,popupMenu.menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { menuItem ->
               when(menuItem.itemId) {
                   R.id.miEdit -> Toast.makeText(mContext,"Edit",Toast.LENGTH_SHORT).show()
                   R.id.miDelete -> Toast.makeText(mContext,"Edit",Toast.LENGTH_SHORT).show()
                   R.id.miFav -> Toast.makeText(mContext,"Add to Favourites",Toast.LENGTH_SHORT).show()
               }
                popupMenu.dismiss()
                true
        }

        }

    }
}