package com.mrindeciso.lib.firebase.storage

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewsImageRepository @Inject constructor(
    storage: FirebaseStorage
) {

    private val mainReference = storage.getReference("news")

    fun getImageReference(path: String) = mainReference.child(path)

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