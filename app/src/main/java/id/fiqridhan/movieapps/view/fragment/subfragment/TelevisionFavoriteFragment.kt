package id.fiqridhan.movieapps.view.fragment.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.adapter.FavoriteTelevisionAdapter
import id.fiqridhan.movieapps.model.MainViewModel
import id.fiqridhan.movieapps.model.Television
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import java.util.*

class TelevisionFavoriteFragment : Fragment() {
    private var mFavoriteTelevisionAdapter: FavoriteTelevisionAdapter? = null
    private var mProgressBar: ProgressBar? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View =
            inflater.inflate(R.layout.fragment_movie_favorite, container, false)
        mProgressBar = rootView.findViewById(R.id.progressbar)
        val mainViewModel: MainViewModel = ViewModelProviders.of(this).get<MainViewModel>(
            MainViewModel::class.java
        )
        mainViewModel.getTelevisionFavorite("television").observe(this, televisionFavorites)
        mFavoriteTelevisionAdapter = activity?.let { FavoriteTelevisionAdapter(it) }
        mFavoriteTelevisionAdapter!!.notifyDataSetChanged()
        mainViewModel.setTelevisionDatabase("television")
        showRecyclerList()
        showLoading(true)
        return rootView
    }

    private fun showRecyclerList() {
        val rvMain = view?.findViewById(R.id.recycler_movie) as? RecyclerView
        rvMain?.layoutManager = LinearLayoutManager(this.context)
        rvMain?.adapter = mFavoriteTelevisionAdapter
        rvMain?.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mProgressBar!!.visibility = View.VISIBLE
        } else {
            mProgressBar!!.visibility = View.GONE
        }
    }

    private val televisionFavorites =
        Observer<ArrayList<Television>> { television ->
            if (television != null) {
                mFavoriteTelevisionAdapter?.setListFavoriteTelevision(television)
                showRecyclerList()
                showLoading(false)
            } else {
                showLoading(false)
            }
        }

    override fun onResume() {
        super.onResume()
        val mainViewModel: MainViewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        mainViewModel.setMovieDatabase("television")
    }
}