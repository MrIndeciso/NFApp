package com.mrindeciso.nfapp.ui.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.UserRepository
import com.mrindeciso.lib.models.User
import com.mrindeciso.lib.preferences.PreferenceManager

class MainActivityViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    fun refreshUser(): LiveData<Boolean> = liveData {
        preferenceManager.currentUser?.let {
            val remoteUser = userRepository.getUserByUID(it.id)

            preferenceManager.currentUser = remoteUser

            emit(remoteUser != it)
        }
    }

    fun updateFCMToken(user: User, newToken: String): LiveData<Boolean> = liveData {
        emit(userRepository.updateUserFCM(user.copy(fcmToken = newToken)))
    }

}