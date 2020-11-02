package com.mrindeciso.lib.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.mrindeciso.lib.models.Subject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SubjectsRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val subjectsCollection = firestore.collection("subjects")

    suspend fun getAllSubjects(): List<Subject> {
        return try {
            subjectsCollection
                .get()
                .await()
                .toObjects(Subject::class.java)
        } catch (e: Exception) {
            listOf()
        }
    }

    suspend fun deleteSubject(subject: Subject): Boolean {
        return try {
            subjectsCollection
                .document(subject.name)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun editSubject(subject: Subject): Boolean {
        return try {
            subjectsCollection
                .document(subject.name)
                .update("otherSynonyms", subject.otherSynonyms)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addSubject(subject: Subject): Boolean {
        return try {
            subjectsCollection
                .document(subject.name)
                .set(subject)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

}