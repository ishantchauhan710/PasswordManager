package com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.fragments

import android.os.Bundle
import android.text.InputType
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.View
import android.view.View.OnTouchListener
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.adapters.LogoCompanyChooserAdapter
import com.ishant.passwordmanager.adapters.PasswordAccountInfoAdapter
import com.ishant.passwordmanager.databinding.BottomSheetOptionsBinding
import com.ishant.passwordmanager.databinding.CompanyChooserSheetBinding
import com.ishant.passwordmanager.databinding.FragmentCreatePasswordBinding
import com.ishant.passwordmanager.db.entities.EncryptedKey
import com.ishant.passwordmanager.db.entities.Entry
import com.ishant.passwordmanager.db.entities.EntryDetail
import com.ishant.passwordmanager.security.EncryptionDecryption.Companion.encrypt
import com.ishant.passwordmanager.ui.activities.create_edit_view_password_activity.CreateEditViewPasswordActivity
import com.ishant.passwordmanager.ui.viewmodels.CreateEditViewPasswordViewModel
import com.ishant.passwordmanager.util.CompanyListData
import com.ishant.passwordmanager.util.Passwords.Companion.PASSWORD1
import com.ishant.passwordmanager.util.Passwords.Companion.PASSWORD2
import kotlinx.coroutines.*


class CreatePasswordFragment : Fragment(R.layout.fragment_create_password) {

    private lateinit var binding: FragmentCreatePasswordBinding
    private lateinit var adapter: PasswordAccountInfoAdapter

    val accountDetailList = mutableListOf<EntryDetail>()
    lateinit var viewModel: CreateEditViewPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePasswordBinding.bind(view)

        val rvAccountDetails = binding.rvAccountDetails
        adapter = PasswordAccountInfoAdapter(accountDetailList)
        rvAccountDetails.adapter = adapter
        rvAccountDetails.layoutManager = LinearLayoutManager(requireContext())



        var companyIcon = R.drawable.cl_general_account

        binding.btnBack.setOnClickListener {
            (activity as CreateEditViewPasswordActivity).finish()
        }

        viewModel = (activity as CreateEditViewPasswordActivity).viewModel

        binding.btnIcon.setOnClickListener {


            val iBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
            val sheetView = layoutInflater.inflate(R.layout.company_chooser_sheet, null)
            iBottomSheetDialog.setContentView(sheetView)
            val companySheetBinding: CompanyChooserSheetBinding = CompanyChooserSheetBinding.bind(
                sheetView
            )
            val companyList = CompanyListData.companyListData
            val companyAdapter = LogoCompanyChooserAdapter(companyList)
            companySheetBinding.rvCompanyChooser.adapter = companyAdapter
            companySheetBinding.rvCompanyChooser.layoutManager = LinearLayoutManager(requireContext())

            companySheetBinding.rvCompanyChooser.setOnTouchListener(OnTouchListener { v, event ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                v.onTouchEvent(event)

                true
            })


            iBottomSheetDialog.show()

            companyAdapter.setOnItemClickListener {
                companyIcon = it.companyIcon
                iBottomSheetDialog.dismiss()
            }

        }

        binding.btnNewEntry.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.account_details_menu, popupMenu.menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.miUsername -> {
                        showBottomSheet(0)
                        popupMenu.dismiss()
                    }
                    R.id.miEmail -> {
                        showBottomSheet(1)
                        popupMenu.dismiss()
                    }
                    R.id.miPhone -> {
                        showBottomSheet(2)
                        popupMenu.dismiss()
                    }
                    R.id.miPassword -> {
                        showBottomSheet(3)
                        popupMenu.dismiss()
                    }
                    R.id.miWebsite -> {
                        showBottomSheet(4)
                        popupMenu.dismiss()
                    }
                    R.id.miNote -> {
                        showBottomSheet(5)
                        popupMenu.dismiss()
                    }


                }
                true
            }


        }


        binding.btnSave.setOnClickListener {
            val entryTitle = binding.entryTitleLayout.editText?.text.toString()

            val entryCategory: String = (binding.categoryChipGroup.children.toList().filter {
                (it as Chip).isChecked
            }[0] as Chip).text.toString()

            val entryIcon = companyIcon

            val entryDetailsList = accountDetailList

            if(entryTitle.isNotEmpty() || entryTitle.isNotBlank()) {
                if(entryCategory.isNotEmpty() || entryCategory.isNotBlank()) {
                    if(entryDetailsList.isNotEmpty()) {

                            val password1 = PASSWORD1
                            val password2 = PASSWORD2

                            CoroutineScope(Dispatchers.IO).launch {

                                val entry = Entry(0,entryTitle,entryCategory,entryIcon)

                                val id = async { viewModel.upsertEntry(entry) }.await()



                                for(entryDetail in entryDetailsList) {

                                    val encryptedObject = encrypt(entryDetail.detailContent,password1,password2)
                                    val encryptedData = encryptedObject.encryptedData
                                    val emdKey = encryptedObject.key1
                                    val eedKey = encryptedObject.key2

                                    entryDetail.id = 0
                                    entryDetail.entryId = id
                                    entryDetail.detailContent = encryptedData

                                    val entryDetailId = async { viewModel.upsertEntryDetail(entryDetail) }.await()
                                    val saltObject = EncryptedKey(0,entryDetailId,emdKey,eedKey)
                                    async { viewModel.upsertEncryptedKey(saltObject) }.await()
                            }

                               withContext(Dispatchers.Main) {
                                   (activity as CreateEditViewPasswordActivity).finish()
                               }

                            }

                    } else {
                        Snackbar.make(view,"You must add at least one detail about your account",Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(view,"You must select a category",Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(view,"Title cannot be blank",Snackbar.LENGTH_SHORT).show()
            }


        }

    }




    private fun showBottomSheet(optionType: Int) {

        val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_options, null)
        mBottomSheetDialog.setContentView(sheetView)

        val sheetBinding: BottomSheetOptionsBinding = BottomSheetOptionsBinding.bind(sheetView)

        var detailType = ""
        var detailContent = ""

        when (optionType) {
            0 -> {
                detailType = "Username"
                sheetBinding.optionInputLayout.helperText = "Eg. user710"
            }
            1 -> {
                detailType = "Email"
                sheetBinding.optionInputLayout.helperText = "Eg. user@example.com"
            }
            2 -> {
                detailType = "Phone Number"
                sheetBinding.optionInputLayout.editText?.inputType =
                    InputType.TYPE_CLASS_PHONE or InputType.TYPE_NUMBER_VARIATION_PASSWORD;
                sheetBinding.optionInputLayout.helperText = "Eg. +91 9876012345"
            }
            3 -> {
                detailType = "Password"
                sheetBinding.optionInputLayout.isPasswordVisibilityToggleEnabled = true
                sheetBinding.optionInputLayout.helperText = "Always keep strong passwords"
            }
            4 -> {
                detailType = "Website"
                sheetBinding.optionInputLayout.helperText = "Eg. www.example.com"
            }
            5 -> {
                detailType = "Notes"
                sheetBinding.optionInputLayout.editText?.minLines = 3
                sheetBinding.optionInputLayout.helperText = "You can add some notes or details here"
                sheetBinding.optionInputLayout.editText?.inputType = TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            }
        }

        sheetBinding.optionInputLayout.hint = detailType

        mBottomSheetDialog.show()

        sheetBinding.btnAddOption.setOnClickListener {
            val validateMessage = validateInput(
                sheetBinding.optionInputLayout.editText?.text.toString(),
                optionType
            )
            if(validateMessage=="Validated") {
                val accountDetailObj = EntryDetail(
                    1,
                    1,
                    detailType,
                    sheetBinding.optionInputLayout.editText?.text.toString()
                )
                accountDetailList.add(accountDetailObj)
                adapter.notifyDataSetChanged()
                mBottomSheetDialog.dismiss()
            } else {
                sheetBinding.optionInputLayout.error = validateMessage
            }
        }

    }

    private fun validateInput(input: String, type: Int): String {
        when (type) {
            0 -> {
                // Validate for Username
                if (nullCheckInput(input)) {
                    return "Validated"
                } else {
                    return "You must fill username"
                }
            }
            1 -> {
                // Validate for Email
                if (nullCheckInput(input)) {
                    if (input.contains("@") && input.contains(".")) {
                        return "Validated"
                    } else {
                        return "Incorrect Email Format"
                    }
                } else {
                    return "Username cannot be blank"
                }
            }
            2 -> {
                // Validate for Phone Number
                if (nullCheckInput(input)) {
                    if (input.length > 2) {
                        return "Validated"
                    } else {
                        return "Phone number should be more than two digits"
                    }
                } else {
                    return "Phone number cannot be blank"
                }
            }
            3 -> {
                // Validate for Password
                if (nullCheckInput(input)) {
                    return "Validated"
                } else {
                    return "Password cannot be blank"
                }
            }
            4 -> {
                // Validate for Website
                if (nullCheckInput(input)) {
                    if (input.contains(".")) {
                        return "Validated"
                    } else {
                        return "Incorrect Website Format"
                    }
                } else {
                    return "Website URL cannot be blank"
                }
            }
            5 -> {
                // Validate for Note
                if (nullCheckInput(input)) {
                    return "Validated"
                } else {
                    return "Note cannot be blank"
                }
            }
            else -> return "An Error Occurred"
        }
    }

    private fun nullCheckInput(input: String): Boolean {
        return input.isNotEmpty() && input.isNotBlank()
    }


}