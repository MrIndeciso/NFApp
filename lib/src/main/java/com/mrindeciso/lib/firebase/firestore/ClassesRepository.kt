package com.mrindeciso.lib.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.mrindeciso.lib.models.SchoolClass
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClassesRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val classesCollection = firestore.collection("classes")

    suspend fun getAllClasses(): List<SchoolClass> {
        return try {
            classesCollection
                .get()
                .await()
                .toObjects(SchoolClass::class.java)
        } catch (e: Exception) {
            listOf()
        }
    }

    suspend fun addClass(schoolClass: SchoolClass): Boolean {
        return try {
            classesCollection
                .document(schoolClass.name)
                .set(schoolClass)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateClass(schoolClass: SchoolClass): Boolean {
        return try {
            classesCollection
                .document(schoolClass.name)
                .update(
                    "course",
                    schoolClass.course,
                    "subjects",
                    schoolClass.subjects,
                    "year",
                    schoolClass.year
                )
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteClass(schoolClass: SchoolClass): Boolean {
        return try {
            classesCollection
                .document(schoolClass.name)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

}