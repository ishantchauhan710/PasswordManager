package com.ishant.passwordmanager.ui.activities.lock_activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ishant.passwordmanager.R
import com.ishant.passwordmanager.databinding.FragmentUpdateLockPasswordBinding


class UpdateLockPasswordFragment : Fragment(R.layout.fragment_update_lock_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUpdateLockPasswordBinding.bind(view)

        binding.changePasswordScrollView.post {
            binding.changePasswordScrollView.fullScroll(View.FOCUS_DOWN)
        }

    }

}