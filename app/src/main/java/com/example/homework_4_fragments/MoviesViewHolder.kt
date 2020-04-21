package com.example.homework_4_fragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesViewHolder (itemItem: View): RecyclerView.ViewHolder(itemItem){

    var sharedPreference: SharedPreference = SharedPreference()

    var movieImg: ImageView = itemItem.findViewById(R.id.imgMovie)
    var movieTitleTxt: TextView = itemItem.findViewById(R.id.titleMovie)
    var movieDescriptionTxt: TextView = itemItem.findViewById(R.id.descriptionMovie)
    var favoriteImg: ImageView = itemItem.findViewById(R.id.imgBtnFavorite)


    fun bind(item: RecyclerMoviesItem) {
        movieImg.setImageResource(item.image)
        movieTitleTxt.text = item.title
        movieDescriptionTxt.text = item.description

        /* If a product exists in shared preferences then set red heart drawable and set a tag */
        if (checkFavoriteItem(item)) {
            favoriteImg.setImageResource(R.drawable.ic_favorite_red_24dp)
            favoriteImg.tag = "red"
        } else {
            favoriteImg.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            favoriteImg.tag = "border"
        }
    }

    /* Checks whether a particular product exists in SharedPreferences */
        fun checkFavoriteItem(checkMovie: RecyclerMoviesItem): Boolean {
        var check = false
        val favorites = sharedPreference.getFavorites(context)
        if (favorites != null) {
            for (movie in favorites) {
                if (movie.equals(checkMovie)) {
                    check = true
                    break
                }
            }
        }
        return check
    }

    init {
        sharedPreference = SharedPreference()
    }
}

