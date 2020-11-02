package com.mrindeciso.nfapp.ui.administration.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.ClassesRepository
import com.mrindeciso.lib.firebase.firestore.CoursesRepository
import com.mrindeciso.lib.firebase.firestore.SubjectsRepository
import com.mrindeciso.lib.models.Course
import com.mrindeciso.lib.models.SchoolClass
import com.mrindeciso.lib.models.Subject

class SchoolClassesViewModel @ViewModelInject constructor(
    private val coursesRepository: CoursesRepository,
    private val subjectsRepository: SubjectsRepository,
    private val classesRepository: ClassesRepository
) : ViewModel() {

    fun getAllClasses(): LiveData<List<SchoolClass>> = liveData {
        emit(classesRepository.getAllClasses())
    }

    fun getAllSubjects(): LiveData<List<Subject>> = liveData {
        emit(subjectsRepository.getAllSubjects())
    }

    fun getAllCourses(): LiveData<List<Course>> = liveData {
        emit(coursesRepository.getAllCourses())
    }

    fun addClass(schoolClass: SchoolClass): LiveData<Boolean> = liveData {
        emit(classesRepository.addClass(schoolClass))
    }

    fun deleteClass(schoolClass: SchoolClass): LiveData<Boolean> = liveData {
        emit(classesRepository.deleteClass(schoolClass))
    }

    fun updateClass(schoolClass: SchoolClass): LiveData<Boolean> = liveData {
        emit(classesRepository.updateClass(schoolClass))
    }

}