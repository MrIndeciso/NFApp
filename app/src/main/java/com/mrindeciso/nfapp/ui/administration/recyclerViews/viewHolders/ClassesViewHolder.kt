package com.mrindeciso.nfapp.ui.administration.recyclerViews.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.SchoolClass
import com.mrindeciso.nfapp.databinding.ViewholderSchoolclassBinding

class ClassesViewHolder(
    private val binding: ViewholderSchoolclassBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        schoolClass: SchoolClass,
        position: Int,
        onDelete: (Int) -> Unit,
        onEdit: (Int) -> Unit,
        onClone: (Int) -> Unit
    ) {
        binding.tvName.text = schoolClass.name
        binding.tvCourse.text = schoolClass.course
        binding.tvYear.text = schoolClass.year.toString()

        binding.bttDelete.onClick {
            onDelete(position)
        }
        binding.bttEdit.onClick {
            onEdit(position)
        }
        binding.bttClone.onClick {
            onClone(position)
        }
    }

}