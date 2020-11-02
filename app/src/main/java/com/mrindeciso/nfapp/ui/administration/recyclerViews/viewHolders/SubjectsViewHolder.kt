package com.mrindeciso.nfapp.ui.administration.recyclerViews.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.Subject
import com.mrindeciso.nfapp.databinding.ViewholderSubjectBinding

class SubjectsViewHolder(
    private val binding: ViewholderSubjectBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subject: Subject, position: Int, onEdit: (Int) -> Unit, onDelete: (Int) -> Unit) {
        binding.tvTitle.text = subject.name
        binding.tvSynonyms.text = subject.otherSynonyms.joinToString("\n")

        binding.bttDelete.onClick {
            onDelete(position)
        }
        binding.bttEdit.onClick {
            onEdit(position)
        }
    }

}