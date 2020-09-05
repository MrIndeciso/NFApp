package com.mrindeciso.nfapp.ui

import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentHomeBinding

class HomeFragment: ViewBoundFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        firebaseAuth = Firebase.auth

        checkLogin()
    }

    private fun checkLogin() {
        if (firebaseAuth.currentUser == null) {
            findNavController().navigate(R.id.newUserFragment)
        }
    }
}