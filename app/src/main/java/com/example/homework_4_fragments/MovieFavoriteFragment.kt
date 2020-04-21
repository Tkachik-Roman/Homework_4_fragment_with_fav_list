package com.example.homework_4_fragments

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*


class MovieFavoriteFragment : Fragment() {
    var favoriteList: ListView? = null
    var sharedPreference: SharedPreference? = null
    var favorites: List<RecyclerMoviesItem?>? = null
    var activity: Activity? = null
    var movieListAdapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(
            R.layout.fragment_movie_list, container,
            false
        )
        // Get favorite items from SharedPreferences.
        sharedPreference = SharedPreference()
        favorites = sharedPreference!!.getFavorites(activity!!)
        if (favorites == null) {
            showAlert(
                getResources().getString(R.string.no_favorites_items),
                getResources().getString(R.string.no_favorites_msg)
            )
        } else {
            if ((favorites as ArrayList<RecyclerMoviesItem?>).size == 0) {
                showAlert(
                    getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg)
                )
            }
            favoriteList = view.findViewById<View>(R.id.recyclerView) as ListView
            if (favorites != null) {
                movieListAdapter = MoviesAdapter(activity!!, favorites as MutableList<RecyclerMoviesItem>)
                favoriteList!!.adapter = movieListAdapter
                favoriteList!!.onItemClickListener =
                    OnItemClickListener { parent, arg1, position, arg3 -> }
                favoriteList!!.setOnItemLongClickListener(OnItemLongClickListener { parent, view, position, id ->
                        val button = view
                            .findViewById<View>(R.id.imgBtnFavorite) as ImageView
                        val tag = button.tag.toString()
                        if (tag.equals("border", ignoreCase = true)) {
                            sharedPreference!!.addFavorite(
                                activity!!,
                                (favorites as MutableList<RecyclerMoviesItem>).get(position)
                            )
                            Toast.makeText(activity, activity!!.resources.getString(R.string.add_favr), Toast.LENGTH_SHORT).show()
                            button.tag = "red"
                            button.setImageResource(R.drawable.ic_favorite_red_24dp)
                        } else {
                            sharedPreference!!.removeFavorite(
                                activity!!,
                                (favorites as MutableList<RecyclerMoviesItem>).get(position)
                            )
                            button.tag = "border"
                            button.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                            movieListAdapter!!.remove(
                                (favorites as MutableList<RecyclerMoviesItem>).get(position)
                            )
                            Toast.makeText(activity, activity!!.resources.getString(R.string.remove_favr), Toast.LENGTH_SHORT).show()
                        }
                        true
                    })
            }
        }
        return view
    }

    fun showAlert(title: String?, message: String?) {
        if (activity != null && !activity!!.isFinishing) {
            val alertDialog = AlertDialog.Builder(activity)
                .create()
            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setCancelable(false)
            // setting OK Button
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "OK"
            ) { dialog, which ->
                dialog.dismiss()
                // activity.finish();
                getFragmentManager()?.popBackStackImmediate()
            }
            alertDialog.show()
        }
    }

    override fun onResume() {
        getActivity()?.setTitle(R.string.favorites)
        getActivity()?.getActionBar()?.setTitle(R.string.favorites)
        super.onResume()
    }

    companion object {
        const val ARG_ITEM_ID = "favorite_list"
    }
}
