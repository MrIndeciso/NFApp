package com.mrindeciso.nfapp.ui

import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: ViewBoundFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun onStart() {
        super.onStart()

        requireActivity().setTitle(R.string.menu_profile)
    }

}