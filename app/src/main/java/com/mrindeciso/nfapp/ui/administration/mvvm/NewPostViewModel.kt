package com.mrindeciso.nfapp.ui.administration.mvvm

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mrindeciso.lib.firebase.firestore.NewsRepository
import com.mrindeciso.lib.firebase.storage.NewsImageRepository
import com.mrindeciso.lib.models.News
import com.mrindeciso.nfapp.utils.models.ImageContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class NewPostViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository,
    private val newsImageRepository: NewsImageRepository,
    private val contentResolver: ContentResolver
) : ViewModel() {

    val imageByteArray = MutableLiveData<ImageContainer?>(null)

    fun convertImageURIToByteArray(uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()

        var displayName = ""

        val cursor = contentResolver.query(uri, null, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }

        imageByteArray.postValue(ImageContainer(displayName, image))
    }

    fun createPost(post: News): LiveData<Boolean> = liveData {
        val loadedImage = imageByteArray.value?.bitmap
        val imageName = imageByteArray.value?.displayName ?: ""

        if (loadedImage != null) {
            val outputStream = ByteArrayOutputStream()
            loadedImage.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
            newsImageRepository.uploadImage(imageName, outputStream.toByteArray())

            val newPost = post.copy(image = imageName)
            emit(newsRepository.postNews(newPost))
        } else {
            emit(newsRepository.postNews(post))
        }
    }

}