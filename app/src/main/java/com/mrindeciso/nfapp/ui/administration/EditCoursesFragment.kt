package com.mrindeciso.nfapp.ui.administration

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrindeciso.lib.extensions.MaterialDialog
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.Course
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.databinding.DialogNewcourseBinding
import com.mrindeciso.nfapp.databinding.FragmentCoursesBinding
import com.mrindeciso.nfapp.ui.administration.mvvm.CoursesViewModel
import com.mrindeciso.nfapp.ui.administration.recyclerViews.CoursesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCoursesFragment :
    ViewBoundFragment<FragmentCoursesBinding>(FragmentCoursesBinding::inflate) {

    private val coursesViewModel by viewModels<CoursesViewModel>()

    private val coursesList = mutableListOf<Course>()

    override fun onStart() {
        super.onStart()

        viewBinding.recView.apply {
            adapter = CoursesAdapter(coursesList, onDelete = {
                coursesViewModel.deleteCourse(coursesList[it]).observe(this@EditCoursesFragment) {
                    updateCourses()
                }
            })
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewBinding.bttNew.onClick {
            MaterialDialog(
                requireContext(),
                DialogNewcourseBinding.inflate(layoutInflater)
            ) { dialog ->
                bttSave.onClick {
                    val course = Course(tilName.editText?.text?.toString() ?: "")
                    coursesViewModel.addCourse(course).observe(this@EditCoursesFragment) {
                        updateCourses()
                    }
                    dialog.dismiss()
                }
            }
        }

        updateCourses()
    }

    private fun updateCourses() {
        coursesViewModel.getAllCourses().observe(this) {
            coursesList.clear()
            coursesList.addAll(it)
            viewBinding.recView.adapter?.notifyDataSetChanged()
        }
    }

}