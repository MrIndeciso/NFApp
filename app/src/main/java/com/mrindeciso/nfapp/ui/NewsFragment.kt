package com.mrindeciso.nfapp.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrindeciso.lib.models.News
import com.mrindeciso.lib.preferences.PreferenceManager
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentNewsBinding
import com.mrindeciso.nfapp.ui.mvvm.NewsViewModel
import com.mrindeciso.nfapp.ui.news.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment: ViewBoundFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val newsViewModel by viewModels<NewsViewModel>()

    private val newsList = mutableListOf<News>()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onStart() {
        super.onStart()

        if (preferenceManager.currentUser == null) {
            findNavController().navigate(R.id.newUserFragment)
        }

        requireActivity().setTitle(R.string.menu_news)

        viewBinding.recView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = NewsAdapter(newsList, loadImage = { pos, imgView ->
                Glide.with(this)
                    .load(newsViewModel.getImageReference(newsList[pos]))
                    .into(imgView)
            })
        }

        newsViewModel.getNews().observe(this, {
            newsList.clear()
            newsList.addAll(it)
            viewBinding.recView.adapter?.notifyDataSetChanged()
        })
    }

}