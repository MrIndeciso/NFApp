package com.mrindeciso.lib.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchoolClass(

    val course: String = "",

    val name: String = "",

    val subjects: List<String> = listOf(),

    val year: Int = 0

) : Parcelable