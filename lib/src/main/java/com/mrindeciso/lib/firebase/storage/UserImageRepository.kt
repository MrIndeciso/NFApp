package com.mrindeciso.lib.firebase.storage

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserImageRepository @Inject constructor(
    storage: FirebaseStorage
) {

    private val mainReference = storage.getReference("users")

    fun getProfilePicReference(path: String) = mainReference.child(path)

    suspend fun deleteImage(path: String): Boolean {
        return try {
            mainReference
                .child(path)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun uploadImage(path: String, image: ByteArray): Boolean {
        return try {
            mainReference
                .child(path)
                .putBytes(image)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

}