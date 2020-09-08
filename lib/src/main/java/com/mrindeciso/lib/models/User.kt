package com.mrindeciso.lib.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class User(

    @Json(name = "id")
    val id: String = "",

    @Json(name = "name")
    val name: String = "",

    @Json(name = "surname")
    val surname: String = "",

    @Json(name = "email")
    val email: String = "",

    @Json(name = "phoneNumber")
    val phoneNumber: String? = null,

    @Json(name = "banned")
    val banned: Boolean = false,

    @Json(name = "permission_level")
    val permission_level: PermissionLevel = PermissionLevel.USER

) {

    fun serialize(): String = adapter.toJson(this)

    companion object {

        private val adapter
            get() = Moshi.Builder().build().adapter(User::class.java)

        fun deserialize(user: String?): User? = try {
            adapter.fromJson(user ?: "")
        } catch (e: Exception) {
            null
        }

    }

    enum class PermissionLevel {
        USER,
        MODERATOR,
        ADMINISTRATOR,
    }

}