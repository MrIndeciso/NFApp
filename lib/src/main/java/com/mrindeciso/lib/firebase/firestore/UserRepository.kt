package com.mrindeciso.lib.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.mrindeciso.lib.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val userCollection = firestore.collection("users")

    suspend fun getUserById(userId: String): User? {
        return try {
            userCollection
                .document(userId)
                .get()
                .await()
                .toObject()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserByUID(uid: String): User? {
        return try {
            userCollection
                .whereEqualTo("id", uid)
                .get()
                .await()
                .documents.let {
                    Log.i("Size", it.size.toString())

                    Log.i("Item", it.first().toString())
                    Log.i("ItemObj", it.first().data?.toString() ?: "")

                    it.firstOrNull()?.toObject()
                }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addUser(user: User): Boolean {
        return try {
            userCollection
                .add(user)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

}