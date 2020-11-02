package com.mrindeciso.lib.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.mrindeciso.lib.models.Course
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CoursesRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val coursesCollection = firestore.collection("courses")

    suspend fun getAllCourses(): List<Course> {
        return try {
            coursesCollection
                .get()
                .await()
                .toObjects(Course::class.java)
        } catch (e: Exception) {
            listOf()
        }
    }

    suspend fun deleteCourse(course: Course): Boolean {
        return try {
            coursesCollection
                .document(course.name)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addCourse(course: Course): Boolean {
        return try {
            coursesCollection
                .document(course.name)
                .set(course)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

}