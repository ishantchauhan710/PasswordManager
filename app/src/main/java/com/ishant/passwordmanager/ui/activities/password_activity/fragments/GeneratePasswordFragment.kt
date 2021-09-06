package com.ishant.passwordmanager.ui.activities.password_activity.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.FragmentGeneratePasswordBinding
import nu.aaro.gustav.passwordstrengthmeter.PasswordStrengthCalculator
import java.util.regex.Pattern

class GeneratePasswordFragment : Fragment(R.layout.fragment_generate_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentGeneratePasswordBinding.bind(view)

        binding.passwordInputMeter.setPasswordStrengthCalculator(object :
            PasswordStrengthCalculator {
            override fun calculatePasswordSecurityLevel(password: String?): Int {
                if (password != null) {
                    return getPasswordScore(password)
                } else {
                    return 0
                }
            }

            override fun getMinimumLength(): Int {
                return 1
            }

            override fun passwordAccepted(level: Int): Boolean {
                return true
            }

            override fun onPasswordAccepted(password: String?) {

            }
        })

        binding.passwordInputMeter.setEditText(binding.etGeneratedPassword)

    }

    private fun getPasswordScore(password: String): Int {
        if(password.length == 0) {
            return 0
        } else if (password.length in 1..3) {
            return 1
        } else if (password.length in 4..8) {
            return 2
        } else if (password.length in 9..12) {
            return 3
        } else if (password.length in 13..18) {
            return 4
        } else {
            return 5
        }
    }

}