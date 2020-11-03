package com.mrindeciso.nfapp.ui.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.UserRepository
import com.mrindeciso.lib.models.User

class AdministrationViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun promoteUser(name: String): LiveData<Boolean> = liveData {
        emit(userRepository.setUserPermissionLevel(name, User.PermissionLevel.ADMINISTRATOR))
    }

}