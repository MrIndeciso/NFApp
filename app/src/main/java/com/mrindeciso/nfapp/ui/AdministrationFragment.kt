package com.mrindeciso.nfapp.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mrindeciso.lib.extensions.MaterialDialog
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.DialogPromoteUserBinding
import com.mrindeciso.nfapp.databinding.FragmentAdministrationBinding
import com.mrindeciso.nfapp.ui.mvvm.AdministrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdministrationFragment :
    ViewBoundFragment<FragmentAdministrationBinding>(FragmentAdministrationBinding::inflate) {

    private val administrationViewModel by viewModels<AdministrationViewModel>()

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

            it.tvPromoteUser.onClick {
                MaterialDialog(
                    requireContext(),
                    DialogPromoteUserBinding.inflate(layoutInflater)
                ) { dialog ->
                    bttSave.onClick {
                        administrationViewModel.promoteUser(tilNewName.editText?.text.toString())
                            .observe(this@AdministrationFragment) {
                                dialog.dismiss()
                            }
                    }
                }
            }
        }
    }

}