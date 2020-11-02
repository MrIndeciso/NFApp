package com.mrindeciso.nfapp.ui.administration.recyclerViews

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.inflateBinding
import com.mrindeciso.lib.models.Course
import com.mrindeciso.nfapp.databinding.ViewholderCourseBinding
import com.mrindeciso.nfapp.ui.administration.recyclerViews.viewHolders.CoursesViewHolder

class CoursesAdapter(
    private val coursesList: List<Course>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<CoursesViewHolder>() {

    override fun getItemCount(): Int = coursesList.size

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.bind(coursesList[position], position, onDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val binding = parent.inflateBinding(ViewholderCourseBinding::inflate)
        return CoursesViewHolder(binding)
    }

}