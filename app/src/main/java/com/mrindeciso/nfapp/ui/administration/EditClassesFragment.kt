package com.mrindeciso.nfapp.ui.administration

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.SchoolClass
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentSchoolClassesEditorBinding
import com.mrindeciso.nfapp.ui.administration.mvvm.SchoolClassesViewModel
import com.mrindeciso.nfapp.ui.administration.recyclerViews.ClassesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditClassesFragment :
    ViewBoundFragment<FragmentSchoolClassesEditorBinding>(FragmentSchoolClassesEditorBinding::inflate) {

    private val schoolClasses = mutableListOf<SchoolClass>()

    private val schoolClassesViewModel by viewModels<SchoolClassesViewModel>()

    override fun onStart() {
        super.onStart()

        viewBinding.recView.apply {
            adapter = ClassesAdapter(schoolClasses, onDelete = {
                schoolClassesViewModel.deleteClass(schoolClasses[it])
                    .observe(this@EditClassesFragment) {
                        loadClasses()
                    }
            }, onClone = {
                newClass(prefill = schoolClasses[it], false)
            }, onEdit = {
                newClass(prefill = schoolClasses[it], true)
            })
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewBinding.bttNew.onClick {
            newClass()
        }

        loadClasses()
    }

    private fun newClass(prefill: SchoolClass? = null, isEdit: Boolean = false) {
        val bundle = bundleOf("prefill" to prefill, "isEdit" to isEdit)
        findNavController().navigate(R.id.newClassFragment, bundle)
    }

    private fun loadClasses() {
        schoolClassesViewModel.getAllClasses().observe(this) {
            schoolClasses.clear()
            schoolClasses.addAll(it)
            viewBinding.recView.adapter?.notifyDataSetChanged()
        }
    }

}