package com.mrindeciso.lib.models

import com.google.firebase.Timestamp

data class News(

    val timestamp: Timestamp = Timestamp.now(),

    val title: String = "",

    val author: String = "",

    val message: String? = null,

    val image: String? = null,

    val disappearAfter: Timestamp? = null

)