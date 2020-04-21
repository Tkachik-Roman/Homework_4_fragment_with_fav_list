package com.example.homework_4_fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(private val layoutInflater: LayoutInflater,
                    private val items: MutableList<RecyclerMoviesItem>,
                    private val listener: (recyclerMoviesItem:RecyclerMoviesItem) -> Unit): RecyclerView.Adapter<MoviesViewHolder>() {

    var movies: MutableList<RecyclerMoviesItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(layoutInflater.inflate(R.layout.item_movies_recycler, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            listener.invoke(items[position])
        }
    }

    fun add(movie: RecyclerMoviesItem) {
        movies?.add(movie)
        notifyDataSetChanged()
    }


    fun remove(movie: RecyclerMoviesItem) {
        movies?.remove(movie)
        notifyDataSetChanged()
    }

    init {
        this.movies = movies
    }
}
