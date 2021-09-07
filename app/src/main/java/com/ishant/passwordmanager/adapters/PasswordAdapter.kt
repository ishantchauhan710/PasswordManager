package com.ishant.passwordmanager.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.PasswordBinding
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import kotlinx.coroutines.*

class PasswordAdapter(private val mContext: Context, private val viewModel: CreateEditViewPasswordViewModel, private val owner: LifecycleOwner, private val fragmentView: View, private val activity: PasswordActivity): RecyclerView.Adapter<PasswordAdapter.PasswordAdapterViewHolder>() {
    inner class PasswordAdapterViewHolder(val binding: PasswordBinding): RecyclerView.ViewHolder(binding.root)


    private val differCallback = object: DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordAdapterViewHolder {
        val view = PasswordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PasswordAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onItemClickListener: ((Entry) -> Unit)? = null


    override fun onBindViewHolder(holder: PasswordAdapterViewHolder, position: Int) {


        val entry = differ.currentList[position]

        viewModel.getAllEntryDetails(entry.id).observe(owner, Observer { entryDetailList ->

            holder.binding.tvPasswordTitle.text = entry.title
            holder.binding.tvPasswordInfo.text = entry.category
            holder.binding.ivPasswordIcon.setImageResource(entry.icon)

            holder.binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(entry)
                }
            }


            holder.binding.ivOptions.setOnClickListener {

                val popupMenu = PopupMenu(mContext, it)
                popupMenu.menuInflater.inflate(R.menu.options_menu, popupMenu.menu)

                if(entry.favourite==0) {
                    popupMenu.menu.get(2).title = "Add to Favourites"
                } else {
                    popupMenu.menu.get(2).title = "Remove from Favourites"
                }

                popupMenu.show()

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.miEdit -> Toast.makeText(mContext, "Edit", Toast.LENGTH_SHORT).show()
                        R.id.miDelete -> {
                            AlertDialog.Builder(mContext)
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to delete this entry named as ${entry.title}")
                                .setPositiveButton("Yes") { d, i ->
                                    for (entryDetail in entryDetailList) {
                                        viewModel.deleteEncryptedKeys(entryDetail.id)
                                    }

                                    viewModel.deleteEntryDetails(entry.id)
                                    viewModel.deleteEntry(entry)
                                    d.dismiss()
                                    Snackbar.make(
                                        fragmentView,
                                        " \"${entry.title}\" Deleted Successfully",
                                        Snackbar.LENGTH_SHORT
                                    ).show()


                                }.setNegativeButton("No") { d, i ->
                                    d.dismiss()
                                }.create().show()

                        }
                        R.id.miFav -> {

                            if(entry.favourite==0) {


                                //viewModel.setFavouriteEntry(1,entry.id)

                                 CoroutineScope(Dispatchers.IO).launch {
                                     entry.favourite = 1
                                     async {viewModel.upsertEntry(entry)}.await()
                                     withContext(Dispatchers.Main) {
                                         Snackbar.make(fragmentView,"Added to Favourites",Snackbar.LENGTH_SHORT).show()
                                         viewModel.getAllEntries().observe(owner, Observer { it1 ->
                                             viewModel.sortedList.postValue(it1)
                                             activity.binding.navView.setSelectionAtPosition(1, true)
                                         })
                                     }
                                 }

                                /*
                                 viewModel.getAllEntries().observe(owner, Observer { it1 ->
                                    viewModel.sortedList.postValue(it1)
                                })
                                activity.binding.navView.setSelection(1)


                                 */

                            } else {


                                CoroutineScope(Dispatchers.IO).launch {
                                    entry.favourite = 0
                                    async {viewModel.upsertEntry(entry)}.await()
                                    withContext(Dispatchers.Main) {
                                        Snackbar.make(fragmentView,"Removed to Favourites",Snackbar.LENGTH_SHORT).show()
                                            viewModel.getAllEntries().observe(owner, Observer { it1 ->
                                                viewModel.sortedList.postValue(it1)
                                                activity.binding.navView.setSelectionAtPosition(1, true)
                                            })
                                    }

                                }

                                /*   viewModel.setFavouriteEntry(0,entry.id)
                                   Snackbar.make(fragmentView,"Removed from Favourites",Snackbar.LENGTH_SHORT).show()
                                   viewModel.getAllEntries().observe(owner, Observer { it1 ->
                                       viewModel.sortedList.postValue(it1)
                                   })
                                   activity.binding.navView.setSelectionAtPosition(1,true)
                                   */

                            }
                        }
                    }
                    popupMenu.dismiss()
                    true
                }
            }



            if (position == differ.currentList.size - 1) {

            }


        })


    }

    fun setOnItemClickListener(listener: (Entry) -> Unit) {
        onItemClickListener = listener
    }

}