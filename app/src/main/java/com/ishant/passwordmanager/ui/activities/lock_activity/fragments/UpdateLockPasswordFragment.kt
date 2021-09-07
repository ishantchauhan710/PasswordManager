package com.ishant.passwordmanager.ui.activities.lock_activity.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.CreateLockBottomSheetBinding
import com.ishant.passwordmanager.databinding.FragmentUpdateLockPasswordBinding
import com.ishant.passwordmanager.db.entities.Lock
import com.ishant.passwordmanager.ui.activities.lock_activity.LockActivity
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity
import kotlinx.coroutines.*


class UpdateLockPasswordFragment : Fragment(R.layout.fragment_update_lock_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUpdateLockPasswordBinding.bind(view)

        binding.changePasswordScrollView.post {
            binding.changePasswordScrollView.fullScroll(View.FOCUS_DOWN)
        }


        var cbBruteForce = 0

        binding.btnBruteForceHelp.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Anti Brute Force Mechanism")
                .setMessage("If you enable this mechanism, you would need to wait for 30 seconds every time you enter a wrong password for three consecutive times. We highly recommend you to enable this option")
                .setPositiveButton("Ok") { d, _ ->
                    d.dismiss()
                }.create().show()
        }


        val viewModel = (activity as LockActivity).viewModel

        viewModel.getLockPassword().observe(viewLifecycleOwner, Observer { lockPassword ->

            binding.btnChangePassword.setOnClickListener {
                val correctOldPassword = lockPassword[0].password
                val oldPassword = binding.layoutOldLockPassword.editText?.text.toString()
                val password = binding.layoutLockPassword.editText?.text.toString()
                val hint = binding.layoutLockPasswordHint.editText?.text.toString()

                if (oldPassword.isEmpty() || oldPassword.isBlank()) {
                    binding.layoutOldLockPassword.isErrorEnabled = true
                    binding.layoutOldLockPassword.error = "Old Password cannot be blank"
                } else {
                    if(oldPassword!=correctOldPassword) {
                        binding.layoutOldLockPassword.isErrorEnabled = true
                        binding.layoutOldLockPassword.error = "Incorrect Old Password"
                    } else {
                        binding.layoutOldLockPassword.isErrorEnabled = false
                        if(binding.cbAntiBruteForce.isChecked) {
                            cbBruteForce = 1
                        } else {
                            cbBruteForce = 0
                        }

                        if (password.isBlank() || password.isEmpty()) {
                            binding.layoutLockPassword.error = "Password cannot be blank"
                        } else {
                            if (password.length <= 3) {
                                binding.layoutLockPassword.error = "Your password should be at least 4 letters long"
                            } else {
                                if (hint.isBlank() || hint.isEmpty()) {
                                    binding.layoutLockPassword.isErrorEnabled = false
                                    binding.layoutLockPasswordHint.error = "Password Hint cannot be blank"
                                } else {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val lock = Lock(0,password,"ishant",hint,cbBruteForce)
                                        async { viewModel.setLock(lock) }.await()
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(requireContext().applicationContext,"Password Changed Successfully",Toast.LENGTH_SHORT).show()
                                            requireActivity().finish()
                                        }
                                      }
                                }
                            }
                        }
                    }

                    }
                }


            })



    }
}