package com.example.newsfeed

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.databinding.NewsRecyclerRowBinding
import com.squareup.picasso.Picasso

class FavoritesAdapter(
    private var favoritesList: MutableList<Favorites>?,
    private val listener: Listener
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val binding = NewsRecyclerRowBinding.bind(item)
        fun buttonStatus() {
            binding.apply {
                favoriteButton.setImageResource(R.drawable.star_active)
                favoriteObserver.isChecked = true
                favoriteButton.setOnClickListener {
                    if (favoriteObserver.isChecked) {
                        favoriteButton.setImageResource(R.drawable.star_empty)
                        favoriteObserver.isChecked = false
                    } else {
                        favoriteButton.setImageResource(R.drawable.star_active)
                        favoriteObserver.isChecked = true
                    }
                }
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_row, parent, false)
        return FavoriteViewHolder(view)
    }

    fun updateData(data: List<Favorites>) {
        favoritesList?.clear()
        favoritesList?.addAll(data)
    }

    override fun getItemCount(): Int {
        return favoritesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val article: Favorites = favoritesList?.get(position) ?: return

        holder.buttonStatus()

        holder.binding.apply {
            articleTitle.text = article.title
            articleSource.text = article.sourceName
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
                if (!isChecked) {
                    listener.onClickFavoriteRemove(article)
                }
            }
        }
    }

    interface Listener{
        fun onClickFavoriteRemove(article: Favorites)
    }
}