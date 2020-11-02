package com.mrindeciso.nfapp.ui.administration.recyclerViews.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.Course
import com.mrindeciso.nfapp.databinding.ViewholderCourseBinding

class CoursesViewHolder(
    private val binding: ViewholderCourseBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course, position: Int, onDelete: (Int) -> Unit) {
        binding.tvTitle.text = course.name

        binding.bttDelete.onClick {
            onDelete(position)
        }
    }

}