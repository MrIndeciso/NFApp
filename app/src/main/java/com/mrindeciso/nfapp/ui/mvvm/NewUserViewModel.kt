package com.mrindeciso.nfapp.ui.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.ClassesRepository
import com.mrindeciso.lib.firebase.firestore.CoursesRepository
import com.mrindeciso.lib.firebase.firestore.UserRepository
import com.mrindeciso.lib.models.Course
import com.mrindeciso.lib.models.SchoolClass
import com.mrindeciso.lib.models.User

class NewUserViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val coursesRepository: CoursesRepository,
    private val classesRepository: ClassesRepository
) : ViewModel() {

    suspend fun getUserByUID(uid: String) = userRepository.getUserByUID(uid)

    suspend fun addUser(user: User) = userRepository.addUser(user)

    fun getAllCourses(): LiveData<List<Course>> = liveData {
        emit(coursesRepository.getAllCourses())
    }

    fun getClassesByCourse(course: String): LiveData<List<SchoolClass>> = liveData {
        emit(classesRepository.getAllClasses().filter { it.course == course })
    }

}