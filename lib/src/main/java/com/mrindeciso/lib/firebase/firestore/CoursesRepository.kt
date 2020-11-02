package com.mrindeciso.lib.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CoursesRepository @Inject constructor(
    firestore: FirebaseFirestore
)