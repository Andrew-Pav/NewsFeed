package com.example.newsfeed

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.databinding.ActivityFavoritesBinding
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FavoritesActivity : AppCompatActivity(), FavoritesAdapter.Listener {
    private lateinit var binding: ActivityFavoritesBinding
    private var adapter: FavoritesAdapter? = null
    //private val favorite = (MutableList::class) as MutableList<Article>

    private val favorites: MutableList<Favorites> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToNews.setOnClickListener {
            finish()
        }

        val favoritesList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY_FAVORITE, FavoritesAll::class.java)
        } else {
            intent.getParcelableExtra(KEY_FAVORITE)
        }

        favoritesList?.let {
            for (i in 0 until favoritesList.favorites.size) {
                favorites.add(favoritesList.favorites[i])
            }
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() = with(binding) {
        favoritesRcView.layoutManager = LinearLayoutManager(this@FavoritesActivity)
        adapter = FavoritesAdapter(favorites, this@FavoritesActivity)
        favoritesRcView.adapter = adapter
    }

    companion object {
        const val KEY_FAVORITE = "KEY_FAVORITE"
    }

    override fun onClickFavoriteRemove(article: Favorites) {
        favorites.remove(article)
        adapter?.notifyDataSetChanged()
    }

    /*fun serialize(file: File, favorites: MutableList<Favorites>) {
        file.outputStream()
            .let (::ObjectOutputStream)
            .use { it.writeObject(favorites) }
    }

    fun deserialize(file: File): MutableList<Favorites> {
        return file.inputStream()
            .let(::ObjectInputStream)
            .use { it.readObject() as MutableList<Favorites> }
    }*/
}