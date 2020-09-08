package com.mrindeciso.nfapp.ui.mvvm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mrindeciso.lib.firebase.firestore.NewsRepository
import com.mrindeciso.lib.models.News

class NewsViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository,
    @Assisted handle: SavedStateHandle
): ViewModel() {

    fun getNews(): LiveData<List<News>> = liveData {
        emit(newsRepository.getAllNews())
    }

    fun postNews(news: News): LiveData<Boolean> = liveData {
        emit(newsRepository.postNews(news))
    }

}