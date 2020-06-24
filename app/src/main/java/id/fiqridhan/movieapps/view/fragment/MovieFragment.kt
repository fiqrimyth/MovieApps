package id.fiqridhan.movieapps.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.adapter.MovieAdapter
import id.fiqridhan.movieapps.adapter.MovieHorizontalAdapter
import id.fiqridhan.movieapps.model.MainViewModel
import id.fiqridhan.movieapps.model.Movie
import java.util.*

class MovieFragment : Fragment() {
    private var mMovieAdapter: MovieAdapter? = null
    private var mMovieHorizontalAdapter: MovieHorizontalAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var mMainViewModel: MainViewModel? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View =
            inflater.inflate(R.layout.fragment_movie, container, false)
        mProgressBar = rootView.findViewById(R.id.progressbar)
        mMainViewModel = Objects.requireNonNull(activity)?.let {
            ViewModelProviders.of(it)
                .get(MainViewModel::class.java)
        }
        mMainViewModel?.movies?.observe(viewLifecycleOwner, movieS)
        mMainViewModel?.setMovie()
        mMainViewModel = Objects.requireNonNull(activity)?.let {
            ViewModelProviders.of(it)
                .get(MainViewModel::class.java)
        }
        mMainViewModel?.nowPlayingMovie?.observe(viewLifecycleOwner, nowPlayingMovies)
        mMainViewModel?.setNowPlayingMovie()
        mMovieAdapter = context?.let { MovieAdapter(it) }
        mMovieHorizontalAdapter = context?.let { MovieHorizontalAdapter(it) }
        mMovieAdapter?.notifyDataSetChanged()
        mMovieHorizontalAdapter?.notifyDataSetChanged()

        showRecyclerList()
        showLoading(true)

        setHasOptionsMenu(true)
        return rootView
    }

    private fun showRecyclerList() {
        val rvMain = view?.findViewById(R.id.recycler_movie) as? RecyclerView
        val rvSecond = view?.findViewById(R.id.recycler_now_playing_movie) as? RecyclerView
        rvMain?.layoutManager = LinearLayoutManager(this.context)
        rvSecond?.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rvMain?.adapter = mMovieAdapter
        rvSecond?.adapter = mMovieHorizontalAdapter
        rvMain?.setHasFixedSize(true)
        rvSecond?.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mProgressBar!!.visibility = View.VISIBLE
        } else {
            mProgressBar!!.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager = Objects.requireNonNull(activity)
            ?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchManager != null) {
            val searchView =
                menu.findItem(R.id.search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            searchView.queryHint = resources.getString(R.string.search_hint_movie)
            searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(activity)?.let {
                            mMainViewModel?.searchMovies
                                ?.observe(it, searchMovies)
                        }
                    }
                    mMainViewModel?.setSearchMovie(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
        val menuItem = menu.findItem(R.id.search)
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                mMainViewModel?.setMovie()
                return true
            }
        })
    }

    private val movieS =
        Observer<ArrayList<Movie>> { movie ->
            if (movie != null) {
                mMovieAdapter?.setListMovie(movie)
                showRecyclerList()
            }
            showLoading(false)
        }

    private val nowPlayingMovies =
        Observer<ArrayList<Movie>> { movie ->
            if (movie != null) {
                mMovieHorizontalAdapter?.setListMovie(movie)
                showRecyclerList()
                showLoading(false)
            } else {
                showLoading(false)
            }
        }

    private val searchMovies =
        Observer<ArrayList<Movie>> { movie ->
            if (movie != null) {
                mMovieAdapter?.setListMovie(movie)
                showRecyclerList()
                showLoading(false)
                if (movie.size === 0)
                    Toast.makeText(context, "No Result", Toast.LENGTH_SHORT).show()
            } else {
                showLoading(false)
            }
        }
}
