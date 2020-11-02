package com.mrindeciso.lib.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ClassesRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val classesCollection = firestore.collection("classes")


}