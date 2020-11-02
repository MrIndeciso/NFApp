package com.mrindeciso.nfapp.ui.administration.recyclerViews.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.models.Subject
import com.mrindeciso.nfapp.databinding.ViewholderClassSubjectBinding

class ClassSubjectsViewHolder(
    private val binding: ViewholderClassSubjectBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subject: Subject, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
        binding.checkBox.text = subject.name
        binding.checkBox.isChecked = checked

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(isChecked)
        }
    }

}