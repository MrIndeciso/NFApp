package com.mrindeciso.lib.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.mrindeciso.lib.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val userCollection = firestore.collection("users")

    suspend fun getUserByUID(uid: String): User? {
        return try {
            userCollection
                .whereEqualTo("id", uid)
                .limit(1)
                .get()
                .await()
                .documents.let {
                    it.firstOrNull()?.toObject()
                }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setUserPermissionLevel(
        name: String,
        permissionLevel: User.PermissionLevel
    ): Boolean {
        return try {
            userCollection
                .whereEqualTo("email", name)
                .limit(1)
                .get()
                .await()
                .documents
                .firstOrNull()
                ?.id
                ?.let {
                    userCollection
                        .document(it)
                        .update("permission_level", permissionLevel.name)
                        .await()
                }
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun getIDbyUID(uid: String): String {
        return try {
            userCollection
                .whereEqualTo("id", uid)
                .limit(1)
                .get()
                .await()
                .documents
                .firstOrNull()
                ?.id
                ?: ""
        } catch (e: Exception) {
            ""
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

    suspend fun updateUserFCM(user: User): Boolean {
        return try {
            userCollection
                .document(getIDbyUID(user.id))
                .update("fcmToken", user.fcmToken)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUserProfilePic(user: User): Boolean {
        return try {
            userCollection
                .document(getIDbyUID(user.id))
                .update("profilePicture", user.profilePicture)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

}