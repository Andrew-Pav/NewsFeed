package com.example.newsfeed

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorites(
    val title: String,
    val sourceName: String,
    val urlToImage: String,
    val url: String
) : Parcelable

@Parcelize
data class FavoritesAll(
    val favorites: MutableList<Favorites>
) : Parcelable
