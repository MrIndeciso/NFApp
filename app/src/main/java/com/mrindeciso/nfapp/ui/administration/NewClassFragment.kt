package com.mrindeciso.nfapp.ui.administration

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.SchoolClass
import com.mrindeciso.lib.models.Subject
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentNewClassBinding
import com.mrindeciso.nfapp.ui.administration.mvvm.SchoolClassesViewModel
import com.mrindeciso.nfapp.ui.administration.recyclerViews.ClassSubjectsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewClassFragment :
    ViewBoundFragment<FragmentNewClassBinding>(FragmentNewClassBinding::inflate) {

    private val schoolClassesVieModel by viewModels<SchoolClassesViewModel>()

    private val subjectsList = mutableListOf<Pair<Subject, Boolean>>()

    private val prefill: SchoolClass?
        get() = arguments?.getParcelable("prefill")

    private val isEdit: Boolean
        get() = arguments?.getBoolean("isEdit") ?: false

    override fun onStart() {
        super.onStart()

        viewBinding.recViewSubjects.apply {
            adapter = ClassSubjectsAdapter(subjectsList)
            layoutManager = LinearLayoutManager(requireContext())
        }

        prefill?.let {
            viewBinding.tilName.editText?.setText(it.name)
            viewBinding.tilYear.editText?.setText(it.year.toString())

            viewBinding.tilName.editText?.isEnabled = !isEdit
        }

        fetchCourses()
        fetchAndCheckSubjects()

        viewBinding.bttSave.onClick {
            val newClass = SchoolClass(
                viewBinding.tilCourse.editText?.text.toString(),
                viewBinding.tilName.editText?.text.toString(),
                subjectsList.filter { it.second }.map { it.first.name },
                viewBinding.tilYear.editText?.text.toString().toIntOrNull() ?: 0
            )

            if (isEdit) {
                schoolClassesVieModel.updateClass(newClass)
            } else {
                schoolClassesVieModel.addClass(newClass)
            }.observe(this) {
                findNavController().navigateUp()
            }
        }
    }

    private fun fetchCourses() {
        schoolClassesVieModel.getAllCourses().observe(this) {
            val mapped = it.map { it.name }

            prefill?.let { schoolClass ->
                (viewBinding.tilCourse.editText as? AutoCompleteTextView)?.setText(schoolClass.course)
            }

            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, mapped)
            (viewBinding.tilCourse.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }

    private fun fetchAndCheckSubjects() {
        schoolClassesVieModel.getAllSubjects().observe(this) {
            subjectsList.clear()
            if (prefill != null) {
                subjectsList.addAll(it.map { Pair(it, prefill!!.subjects.contains(it.name)) })
            } else {
                subjectsList.addAll(it.map { Pair(it, false) })
            }
            viewBinding.recViewSubjects.adapter?.notifyDataSetChanged()
        }
    }

}