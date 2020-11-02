package com.mrindeciso.nfapp.ui.administration.recyclerViews

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.inflateBinding
import com.mrindeciso.lib.models.Subject
import com.mrindeciso.nfapp.databinding.ViewholderClassSubjectBinding
import com.mrindeciso.nfapp.ui.administration.recyclerViews.viewHolders.ClassSubjectsViewHolder

class ClassSubjectsAdapter(
    private val subjectsList: MutableList<Pair<Subject, Boolean>>
) : RecyclerView.Adapter<ClassSubjectsViewHolder>() {

    override fun getItemCount(): Int = subjectsList.size

    override fun onBindViewHolder(holder: ClassSubjectsViewHolder, position: Int) {
        holder.bind(subjectsList[position].first, subjectsList[position].second) {
            try {
                subjectsList[position] = Pair(subjectsList[position].first, it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassSubjectsViewHolder {
        val binding = parent.inflateBinding(ViewholderClassSubjectBinding::inflate)
        return ClassSubjectsViewHolder(binding)
    }

}