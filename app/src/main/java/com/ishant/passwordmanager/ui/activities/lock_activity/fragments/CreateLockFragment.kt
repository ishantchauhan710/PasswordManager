package com.ishant.passwordmanager.ui.activities.lock_activity.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.CreateLockBottomSheetBinding
import com.ishant.passwordmanager.databinding.FragmentCreateLockBinding
import com.ishant.passwordmanager.db.entities.Lock
import com.ishant.passwordmanager.ui.activities.lock_activity.LockActivity
import com.ishant.passwordmanager.ui.activities.password_activity.PasswordActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateLockFragment : Fragment(R.layout.fragment_create_lock) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCreateLockBinding.bind(view)

        binding.btnGetStarted.setOnClickListener {
            val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
            val sheetView = layoutInflater.inflate(R.layout.create_lock_bottom_sheet, null)
            mBottomSheetDialog.setContentView(sheetView)

            val mBottomSheetBinding = CreateLockBottomSheetBinding.bind(sheetView)
            var cbBruteForce = 0

            mBottomSheetBinding.btnBruteForceHelp.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Anti Brute Force Mechanism")
                    .setMessage("If you enable this mechanism, you would need to wait for 30 seconds every time you enter a wrong password for three consecutive times. We highly recommend you to enable this option")
                    .setPositiveButton("Ok") { d, _ ->
                        d.dismiss()
                    }.create().show()
            }

            mBottomSheetDialog.show()

            val viewModel = (activity as LockActivity).viewModel
            mBottomSheetBinding.btnCreateAccount.setOnClickListener {
                val password = mBottomSheetBinding.layoutLockPassword.editText?.text.toString()
                val hint = mBottomSheetBinding.layoutLockPasswordHint.editText?.text.toString()

                if(mBottomSheetBinding.cbAntiBruteForce.isChecked) {
                    cbBruteForce = 1
                } else {
                    cbBruteForce = 0
                }

                if (password.isBlank() || password.isEmpty()) {
                    mBottomSheetBinding.layoutLockPassword.error = "Password cannot be blank"
                } else {
                    if (password.length <= 3) {
                        mBottomSheetBinding.layoutLockPassword.error = "Your password should be at least 4 letters long"
                    } else {
                        if (hint.isBlank() || hint.isEmpty()) {
                            mBottomSheetBinding.layoutLockPassword.isErrorEnabled = false
                            mBottomSheetBinding.layoutLockPasswordHint.error = "Password Hint cannot be blank"
                        } else {
                            CoroutineScope(Dispatchers.IO).launch {
                                val lock = Lock(0,password,"ishant",hint,cbBruteForce)
                                async { viewModel.setLock(lock) }.await()
                                val intent = Intent(requireContext(), PasswordActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            }
                        }
                    }
                }
            }

        }
    }

}

