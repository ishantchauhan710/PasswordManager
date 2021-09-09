package com.ishant.passwordmanager.ui.activities.lock_activity.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.FragmentLockPasswordBinding
import com.ishant.passwordmanager.ui.activities.lock_activity.LockActivity
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity
import kotlinx.coroutines.*


class LockPasswordFragment : Fragment(R.layout.fragment_lock_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (activity as LockActivity).viewModel
        val binding = FragmentLockPasswordBinding.bind(view)

        var incorrectPasswordCount = 0

        viewModel.getLockPassword().observe(viewLifecycleOwner, Observer { lockData ->

            binding.layoutLockPassword.helperText = "Password Hint: ${lockData[0].hint}"

            binding.btnLoginAccount.setOnClickListener {
                val correctPassword = lockData[0].password
                val password = binding.layoutLockPassword.editText?.text.toString()
                if(password.isEmpty() || password.isBlank()) {
                    Snackbar.make(view,"Password cannot be blank",Snackbar.LENGTH_SHORT).show()
                } else {
                    if(password == correctPassword) {
                        val intent = Intent(requireContext(),PasswordActivity::class.java)
                        startActivity(intent)
                        (activity as LockActivity).finish()
                    } else {
                        Snackbar.make(view,"Incorrect Password!",Snackbar.LENGTH_SHORT).show()

                        if(lockData[0].antiBruteforceEnabled==1) {
                            incorrectPasswordCount += 1
                            if(incorrectPasswordCount>=4) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    var timer = 60
                                    while(timer>0) {
                                        withContext(Dispatchers.Main) {
                                            binding.tvAntiBruteforceCountdown.visibility = View.VISIBLE
                                            binding.tvAntiBruteforceCountdown.text = "Try again after $timer Seconds"
                                            binding.layoutLockPassword.editText?.isEnabled = false
                                            binding.btnLoginAccount.isEnabled = false

                                        }
                                        delay(1000)
                                        timer--
                                    }
                                    withContext(Dispatchers.Main) {
                                        incorrectPasswordCount = 0
                                        binding.tvAntiBruteforceCountdown.visibility = View.GONE
                                        binding.layoutLockPassword.editText?.isEnabled = true
                                        binding.btnLoginAccount.isEnabled = true
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