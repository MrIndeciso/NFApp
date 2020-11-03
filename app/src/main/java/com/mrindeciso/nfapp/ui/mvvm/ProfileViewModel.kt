package com.mrindeciso.nfapp.ui.mvvm

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.UserRepository
import com.mrindeciso.lib.firebase.storage.UserImageRepository
import com.mrindeciso.lib.preferences.PreferenceManager
import kotlinx.coroutines.Dispatchers
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val userImageRepository: UserImageRepository,
    private val preferenceManager: PreferenceManager,
    private val contentResolver: ContentResolver
) : ViewModel() {

    fun getProfilePicReference(path: String) = userImageRepository.getProfilePicReference(path)

    fun postNewProfilePic(newImage: Uri): LiveData<Boolean> = liveData(Dispatchers.IO) {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(newImage, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()

        val randomString = UUID.randomUUID().toString()

        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
        userImageRepository.uploadImage(randomString, outputStream.toByteArray())

        preferenceManager.currentUser?.profilePicture?.let {
            userImageRepository.deleteImage(it)
        }

        preferenceManager.currentUser?.let {
            val newUser = it.copy(profilePicture = randomString)

            emit(userRepository.updateUserProfilePic(newUser))
        }
    }

}