package com.mrindeciso.nfapp.ui

import androidx.navigation.fragment.findNavController
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentAdministrationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdministrationFragment :
    ViewBoundFragment<FragmentAdministrationBinding>(FragmentAdministrationBinding::inflate) {

    override fun onStart() {
        super.onStart()

        requireActivity().setTitle(R.string.menu_administration)

        withBinding {
            it.tvNewPost.onClick {
                findNavController().navigate(R.id.newPostFragment)
            }

            it.tvEditClasses.onClick {
                findNavController().navigate(R.id.editClassesFragment)
            }

            it.tvEditCourses.onClick {
                findNavController().navigate(R.id.editCoursesFragment)
            }

            it.tvEditSubjects.onClick {
                findNavController().navigate(R.id.editSubjectsFragment)
            }
        }
    }

}