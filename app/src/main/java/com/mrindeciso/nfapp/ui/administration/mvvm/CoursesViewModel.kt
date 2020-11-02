package com.mrindeciso.nfapp.ui.administration.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.CoursesRepository
import com.mrindeciso.lib.models.Course

class CoursesViewModel @ViewModelInject constructor(
    private val coursesRepository: CoursesRepository
) : ViewModel() {

    fun getAllCourses(): LiveData<List<Course>> = liveData {
        emit(coursesRepository.getAllCourses())
    }

    fun addCourse(new: Course): LiveData<Boolean> = liveData {
        emit(coursesRepository.addCourse(new))
    }

    fun deleteCourse(deleted: Course): LiveData<Boolean> = liveData {
        emit(coursesRepository.deleteCourse(deleted))
    }

}