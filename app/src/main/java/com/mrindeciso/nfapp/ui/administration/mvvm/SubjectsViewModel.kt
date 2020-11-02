package com.mrindeciso.nfapp.ui.administration.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.SubjectsRepository
import com.mrindeciso.lib.models.Subject

class SubjectsViewModel @ViewModelInject constructor(
    private val subjectsRepository: SubjectsRepository
) : ViewModel() {

    fun getAllSubjects(): LiveData<List<Subject>> = liveData {
        emit(subjectsRepository.getAllSubjects())
    }

    fun addSubject(new: Subject): LiveData<Boolean> = liveData {
        emit(subjectsRepository.addSubject(new))
    }

    fun updateSubject(updated: Subject): LiveData<Boolean> = liveData {
        emit(subjectsRepository.editSubject(updated))
    }

    fun deleteSubject(deleted: Subject): LiveData<Boolean> = liveData {
        emit(subjectsRepository.deleteSubject(deleted))
    }

}