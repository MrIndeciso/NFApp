package com.mrindeciso.nfapp.ui.administration.recyclerViews

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.inflateBinding
import com.mrindeciso.lib.models.Subject
import com.mrindeciso.nfapp.databinding.ViewholderSubjectBinding

class SubjectsAdapter(
    private val subjectsList: List<Subject>,
    private val onClickDelete: (Int) -> Unit,
    private val onClickEdit: (Int) -> Unit
) : RecyclerView.Adapter<SubjectsViewHolder>() {

    override fun getItemCount(): Int = subjectsList.size

    override fun onBindViewHolder(holder: SubjectsViewHolder, position: Int) {
        holder.bind(subjectsList[position], position, onClickEdit, onClickDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsViewHolder {
        val binding = parent.inflateBinding(ViewholderSubjectBinding::inflate)
        return SubjectsViewHolder(binding)
    }

}