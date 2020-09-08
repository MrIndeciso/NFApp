package com.mrindeciso.nfapp.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.mrindeciso.lib.models.News
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentNewsBinding
import com.mrindeciso.nfapp.ui.mvvm.NewsViewModel
import com.mrindeciso.nfapp.ui.news.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: ViewBoundFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val newsViewModel by viewModels<NewsViewModel>()

    private val newsList = mutableListOf<News>()

    override fun onStart() {
        super.onStart()

        requireActivity().setTitle(R.string.menu_news)

        viewBinding.recView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = NewsAdapter(newsList)
        }

        newsViewModel.getNews().observe(this, {
            newsList.clear()
            newsList.addAll(it)
            viewBinding.recView.adapter?.notifyDataSetChanged()
        })

        newsViewModel.postNews(
            News(Timestamp.now(), "Title", "Author", "Message")
        ).observe(this, {
            Log.i("Result", it.toString())
        })
    }

}