package com.mrindeciso.lib.preferences

import android.content.Context
import com.mrindeciso.lib.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var currentUser: User?
        get() = User.deserialize(preferences.getString(Keys.CURRENTUSER.name, null))
        set(value) = preferences.edit().putString(Keys.CURRENTUSER.name, value!!.serialize()).apply()

    private enum class Keys {
        CURRENTUSER,
    }

}