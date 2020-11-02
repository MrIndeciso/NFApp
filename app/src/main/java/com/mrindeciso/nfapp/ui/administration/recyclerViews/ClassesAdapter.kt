package com.mrindeciso.nfapp.ui.administration.recyclerViews

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.inflateBinding
import com.mrindeciso.lib.models.SchoolClass
import com.mrindeciso.nfapp.databinding.ViewholderSchoolclassBinding
import com.mrindeciso.nfapp.ui.administration.recyclerViews.viewHolders.ClassesViewHolder

class ClassesAdapter(
    private val classesList: List<SchoolClass>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Int) -> Unit,
    private val onClone: (Int) -> Unit
) : RecyclerView.Adapter<ClassesViewHolder>() {

    override fun getItemCount(): Int = classesList.size

    override fun onBindViewHolder(holder: ClassesViewHolder, position: Int) {
        holder.bind(classesList[position], position, onDelete, onEdit, onClone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassesViewHolder {
        val binding = parent.inflateBinding(ViewholderSchoolclassBinding::inflate)
        return ClassesViewHolder(binding)
    }

}