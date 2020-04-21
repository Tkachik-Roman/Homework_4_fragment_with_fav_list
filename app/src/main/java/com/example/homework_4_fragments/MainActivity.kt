package com.example.homework_4_fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : FragmentActivity(), MovieListFragment.OnMoviesClickListener  {

    private var contentFragment: Fragment? = null
    lateinit var movListFragment: MovieListFragment
    lateinit var favListFragment: MovieFavoriteFragment

    override fun onMoviesClick(item: RecyclerMoviesItem) {
        openMoviesDetailed(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager: FragmentManager = supportFragmentManager

         //This is called when orientation is changed.
         if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                val content = savedInstanceState.getString("content")
                if (content == MovieFavoriteFragment.ARG_ITEM_ID) {
                    if (fragmentManager.findFragmentByTag(MovieFavoriteFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.favorites)
                        contentFragment = fragmentManager
                            .findFragmentByTag(MovieFavoriteFragment.ARG_ITEM_ID)
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(MovieListFragment.ARG_ITEM_ID) != null) {
                movListFragment = fragmentManager
                    .findFragmentByTag(MovieListFragment.ARG_ITEM_ID) as MovieListFragment
                contentFragment = movListFragment
            }
        } else {
            movListFragment = MovieListFragment()
            setFragmentTitle(R.string.app_name)
            switchContent(movListFragment, MovieListFragment.ARG_ITEM_ID)
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.btnNav)

        // show movieListFragment as default fragment when app is open
        movListFragment = MovieListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MovieListFragment(), MovieListFragment.TAG)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    movListFragment = MovieListFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,  movListFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.favorite -> {
                    favListFragment = MovieFavoriteFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, favListFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (contentFragment is MovieFavoriteFragment) {
            outState.putString("content", MovieFavoriteFragment.ARG_ITEM_ID)
        } else {
            outState.putString("content", MovieListFragment.ARG_ITEM_ID)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorites -> {
                setFragmentTitle(R.string.favorites)
                favListFragment = MovieFavoriteFragment()
                switchContent(favListFragment, MovieFavoriteFragment.ARG_ITEM_ID)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun switchContent(fragment: Fragment?, tag: String?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        while (fragmentManager.popBackStackImmediate());
        if (fragment != null) {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment, tag)
            //Only FavoriteListFragment is added to the back stack.
            if (fragment !is MovieListFragment) {
                transaction.addToBackStack(tag)
            }
            transaction.commit()
            contentFragment = fragment
        }
    }

    protected fun setFragmentTitle(resourseId: Int) {
        setTitle(resourseId)
        actionBar.setTitle(resourseId)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is MovieListFragment) {
            fragment.listener = this
        }
    }

    fun openMoviesDetailed(item: RecyclerMoviesItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MoviesDetailedFragment.newInstance(item.image, item.description), MoviesDetailedFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    /*
     * We call super.onBackPressed(); when the stack entry count is > 0. if it
     * is instanceof MovieListFragment or if the stack entry count is == 0, then
     * we finish the activity.
     * In other words, from MovieListFragment on back press it quits the app.
     */
    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed()
        } else if (contentFragment is MovieListFragment
            || fm.getBackStackEntryCount() === 0
        ) {
            finish()
        }
    }
}