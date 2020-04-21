package com.example.homework_4_fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import java.util.*


class SharedPreference {
    // This four methods are used for maintaining favorites.
    fun saveFavorites(context: Context, favorites: List<RecyclerMoviesItem>) {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: Editor = settings.edit()
        val gson = Gson()
        val jsonFavorites: String = gson.toJson(favorites)
        editor.putString(FAVORITES, jsonFavorites)
        editor.apply()
    }

    fun addFavorite(context: Context, recyclerMoviesItem: RecyclerMoviesItem) {
        var favorites: MutableList<RecyclerMoviesItem>? = getFavorites(context)
        if (favorites == null) favorites = ArrayList<RecyclerMoviesItem>()
        favorites.add(recyclerMoviesItem)
        saveFavorites(context, favorites)
    }

    fun removeFavorite(context: Context, recyclerMoviesItem: RecyclerMoviesItem) {
        val favorites: ArrayList<RecyclerMoviesItem>? = getFavorites(context)
        if (favorites != null) {
            favorites.remove(recyclerMoviesItem)
            saveFavorites(context, favorites)
        }
    }

    fun getFavorites(context: Context): ArrayList<RecyclerMoviesItem>? {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var favorites: List<RecyclerMoviesItem>
        if (settings.contains(FAVORITES)) {
            val jsonFavorites =
                settings.getString(FAVORITES, null)
            val gson = Gson()
            val favoriteItems: Array<RecyclerMoviesItem> =
                gson.fromJson(jsonFavorites, Array<RecyclerMoviesItem>::class.java)
            favorites = Arrays.asList<RecyclerMoviesItem>(*favoriteItems)
            favorites = ArrayList<RecyclerMoviesItem>(favorites)
        } else return null
        return favorites
    }

    companion object {
        const val PREFS_NAME = "MOVIE_APP"
        const val FAVORITES = "Movie_Favorite"
    }
}