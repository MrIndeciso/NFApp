package com.mrindeciso.nfapp.ui

import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrindeciso.lib.extensions.MaterialDialog
import com.mrindeciso.lib.extensions.message
import com.mrindeciso.lib.network.ConnectivityManager
import com.mrindeciso.lib.preferences.PreferenceManager
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: ViewBoundFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onStart() {
        super.onStart()

        requireActivity().setTitle(R.string.menu_home)

        //checkInternet()
        checkLogin()
    }

    private fun checkLogin() {
        if (preferenceManager.currentUser == null) {
            findNavController().navigate(R.id.newUserFragment)
        }
    }

    private fun checkInternet() {
        if (!connectivityManager.isInternetAvailable) {
            MaterialDialog(requireContext()) {
                message(textRes = R.string.error_no_internet_connection)
            }
        }
    }
}