package com.mrindeciso.lib.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mrindeciso.lib.models.News
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewsRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val newsCollection = firestore.collection("news")

    suspend fun getAllNews(): List<News> {
        return try {
            newsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(News::class.java)
        } catch (e: Exception) {
            listOf()
        }
    }

    suspend fun postNews(news: News): Boolean {
        return try {
            newsCollection
                .add(news)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

}