package com.example.newsfeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.databinding.NewsRecyclerRowBinding
import com.kwabenaberko.newsapilib.models.Article
import com.squareup.picasso.Picasso

class NewsRecyclerAdapter(
    private var articleList: MutableList<Article>?,
    private val listener: Listener
) : RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>(){

    class NewsViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val binding = NewsRecyclerRowBinding.bind(item)
        fun likeObserve() {
            binding.apply {
                likeObserver.visibility = View.GONE
                likeButton.setOnClickListener {
                    if (likeObserver.isChecked) {
                        likeButton.setImageResource(R.drawable.like_empty)
                        likeObserver.isChecked = false
                    } else {
                        likeButton.setImageResource(R.drawable.like_active)
                        likeObserver.isChecked = true
                    }
                }
            }
        }
        fun favoriteObserver() {
            binding.apply {
                favoriteObserver.visibility = View.GONE
                favoriteButton.setOnClickListener {
                    if (favoriteObserver.isChecked) {
                        favoriteButton.setImageResource(R.drawable.star_empty)
                        favoriteObserver.isChecked = false
                    } else {
                        favoriteButton.setImageResource(R.drawable.star_active)
                        favoriteObserver.isChecked = true
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_row, parent, false)
        return NewsViewHolder(view)
    }

    fun updateData(data: List<Article>) {
        articleList?.clear()
        articleList?.addAll(data)
    }

    override fun getItemCount(): Int {
        return articleList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article: Article = articleList?.get(position) ?: return

        holder.likeObserve()// вкл/выкл кнопка лайка
        holder.favoriteObserver()// вкл/выкл кнопка избранных

        holder.binding.apply {
            articleTitle.text = article.title
            articleSource.text = article.source.name
            Picasso.get().load(article.urlToImage)
                .error(R.drawable.no_image_icon)
                .placeholder(R.drawable.no_image_icon)
                .into(holder.binding.articleImageView)

            articleImageView.setOnClickListener { view ->
                val intent = Intent(view.context, NewsFullActivity::class.java)
                intent.putExtra("url", article.url)
                view.context.startActivity(intent)
            }

            favoriteObserver.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val favorites = Favorites(
                        article.title,
                        article.source.name,
                        article.urlToImage,
                        article.url
                    )
                    listener.onClickFavorite(favorites)
                }
            }
        }
    }

    interface Listener{
        fun onClickFavorite(favorites: Favorites)
    }
}