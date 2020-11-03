package com.mrindeciso.nfapp.ui

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.preferences.PreferenceManager
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentProfileBinding
import com.mrindeciso.nfapp.ui.mvvm.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment: ViewBoundFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    companion object {
        private const val REQUEST_CODE = 6912
    }

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onStart() {
        super.onStart()

        if (preferenceManager.currentUser == null) {
            findNavController().navigate(R.id.newUserFragment)
        }

        requireActivity().setTitle(R.string.menu_profile)

        preferenceManager.currentUser?.let { user ->
            withBinding {
                it.tvFullName.text = "${user.name} ${user.surname}"
                it.tvUserCourse.text = "${user.schoolClass} - ${user.course}"

                if (user.profilePicture != null) {
                    Glide.with(this)
                        .load(profileViewModel.getProfilePicReference(user.profilePicture!!))
                        .into(it.ivProfilePic)
                } else {
                    it.ivProfilePic.setImageResource(R.drawable.ic_account_circle)
                }

                it.ivProfilePic.onClick {
                    Intent(Intent.ACTION_OPEN_DOCUMENT).also {
                        it.addCategory(Intent.CATEGORY_OPENABLE)
                        it.type = "image/*"
                        startActivityForResult(it, REQUEST_CODE)
                    }
                }

                it.bttLogout.onClick {
                    preferenceManager.currentUser = null
                    activity?.recreate()
                }

                it.bttDeleteProfilePicture.onClick {
                    profileViewModel.deleteProfilePic().observe(this) {
                        activity?.recreate()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                profileViewModel.postNewProfilePic(uri).observe(this) {
                    activity?.recreate()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}