package com.example.homework_4_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_movies_detailed.*
import kotlinx.android.synthetic.main.fragment_movies_detailed.view.*

class MoviesDetailedFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_detailed, container, false)

    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDescriptionDetail.text = arguments?.getString(EXTRA_TITLE, "")
        arguments?.getInt(EXTRA_IMG)?.let {
            view.imgMovieDetail.setImageResource(
                it
            )
        }
    }

    companion object {
        const val TAG = "MovieDetailedFragment"

        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_IMG = "EXTRA_IMG"

        fun newInstance(imageMovieDetailed: Int?, titleMovieDetailed: String): MoviesDetailedFragment {
            val fragment = MoviesDetailedFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_TITLE, titleMovieDetailed)
            bundle.putInt(EXTRA_IMG, imageMovieDetailed!!)
            fragment.arguments = bundle
            return fragment
        }
    }
}