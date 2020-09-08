package com.mrindeciso.nfapp.ui.news

import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.models.News
import com.mrindeciso.nfapp.databinding.ViewholderNewsBinding

class NewsViewHolder(
    private val binding: ViewholderNewsBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News) {
        binding.apply {
            tvTimestamp.text = news.timestamp.toDate().toString()
            tvTitle.text = news.title
            tvAuthor.text = news.author
            tvMessage.text = news.message
        }
    }

}