package com.dicoding.shavemax.ui.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.shavemax.data.remote.response.ArticlesItem
import com.dicoding.shavemax.databinding.CardviewNewsBinding

class NewsAdapter : ListAdapter<ArticlesItem, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(val binding: CardviewNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            binding.tvNewsName.text = item.title
            binding.tvNewsDesc.text = item.description
            Glide.with(binding.root)
                .load(item.urlToImage)
                .into(binding.ivNewsPic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CardviewNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articlesItem = getItem(position)
        holder.bind(articlesItem)
        holder.itemView.setOnClickListener {
            val articlesUrl = articlesItem.url
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articlesUrl))
            holder.itemView.context.startActivity(browserIntent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}