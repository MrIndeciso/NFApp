package com.mrindeciso.nfapp.ui.administration

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrindeciso.lib.extensions.MaterialDialog
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.Subject
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.databinding.DialogNewsubjectBinding
import com.mrindeciso.nfapp.databinding.FragmentSubjectsBinding
import com.mrindeciso.nfapp.ui.administration.mvvm.SubjectsViewModel
import com.mrindeciso.nfapp.ui.administration.recyclerViews.SubjectsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditSubjectsFragment :
    ViewBoundFragment<FragmentSubjectsBinding>(FragmentSubjectsBinding::inflate) {

    private val subjectsViewModel by viewModels<SubjectsViewModel>()

    private val subjectsList = mutableListOf<Subject>()

    override fun onStart() {
        super.onStart()

        viewBinding.recView.apply {
            adapter = SubjectsAdapter(subjectsList, onClickEdit = {
                newSubject(subjectsList[it])
            }, onClickDelete = {
                subjectsViewModel.deleteSubject(subjectsList[it])
                    .observe(this@EditSubjectsFragment) {
                        loadSubjects()
                    }
            })
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewBinding.bttNewSubject.onClick {
            newSubject()
        }

        loadSubjects()
    }

    private fun loadSubjects() {
        subjectsViewModel.getAllSubjects().observe(this) {
            subjectsList.clear()
            subjectsList.addAll(it)
            viewBinding.recView.adapter?.notifyDataSetChanged()
        }
    }

    private fun newSubject(editInto: Subject? = null) {
        MaterialDialog(
            requireContext(),
            DialogNewsubjectBinding.inflate(layoutInflater)
        ) { dialog ->
            editInto?.let {
                tietName.setText(it.name)
                tietName.isEnabled = false
                tietSynonyms.setText(it.otherSynonyms.joinToString("\n"))
            }

            bttSave.onClick {
                val newSubject =
                    Subject(tietName.text.toString(), tietSynonyms.text.toString().split("\n"))
                if (editInto == null) {
                    subjectsViewModel.addSubject(newSubject)
                } else {
                    subjectsViewModel.updateSubject(newSubject)
                }.observe(this@EditSubjectsFragment) {
                    loadSubjects()
                }
                dialog.dismiss()
            }
        }
    }

}