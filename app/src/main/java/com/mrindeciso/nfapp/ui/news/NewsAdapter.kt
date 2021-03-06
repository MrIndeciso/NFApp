package com.mrindeciso.nfapp.ui.news

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mrindeciso.lib.extensions.inflateBinding
import com.mrindeciso.lib.models.News
import com.mrindeciso.nfapp.databinding.ViewholderNewsBinding

class NewsAdapter(
    private val newsList: List<News>,
    private val loadImage: (Int, ImageView) -> Unit
): RecyclerView.Adapter<NewsViewHolder>() {

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position], position, loadImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = parent.inflateBinding(ViewholderNewsBinding::inflate)
        return NewsViewHolder(binding)
    }

}