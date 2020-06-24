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
import id.fiqridhan.movieapps.adapter.TvAdapter
import id.fiqridhan.movieapps.adapter.TvHorizontalAdapter
import id.fiqridhan.movieapps.model.MainViewModel
import id.fiqridhan.movieapps.model.Television
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

class TelevisionFragment : Fragment() {
    private var mTvAdapter: TvAdapter? = null
    private var mTvHorizontalAdapter: TvHorizontalAdapter? = null
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

        mMainViewModel?.televisions?.observe(viewLifecycleOwner, televisions)
        mMainViewModel?.setTelevision()
        mMainViewModel = Objects.requireNonNull(activity)?.let {
            ViewModelProviders.of(it)
                .get(MainViewModel::class.java)
        }

        mMainViewModel?.nowPlayingTelevision?.observe(viewLifecycleOwner, nowPlayingTvs)
        mMainViewModel?.setNowPlayingTelevision()
        mTvAdapter = activity?.let { TvAdapter(it) }
        mTvHorizontalAdapter = activity?.let { TvHorizontalAdapter(it) }

        mTvAdapter?.notifyDataSetChanged()
        mTvHorizontalAdapter?.notifyDataSetChanged()

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
        rvMain?.adapter = mTvAdapter
        rvSecond?.adapter = mTvHorizontalAdapter
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
            searchView.queryHint = resources.getString(R.string.search_hint_tv)
            searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onQueryTextSubmit(query: String): Boolean {
                    Objects.requireNonNull(activity)?.let {
                        mMainViewModel?.searchTv?.observe(
                            it,
                            searchTelevisions
                        )
                    }
                    mMainViewModel?.setSearchTelevision(query)
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
                mMainViewModel?.setTelevision()
                return true
            }
        })
    }

    private val televisions =
        Observer<ArrayList<Television>> { television ->
            if (television != null) {
                mTvAdapter?.setListTelevision(television)
                showRecyclerList()
                showLoading(false)
            } else {
                showLoading(false)
            }
        }
    private val nowPlayingTvs =
        Observer<ArrayList<Television>> { television ->
            if (television != null) {
                mTvHorizontalAdapter?.setListTelevision(television)
                showRecyclerList()
                showLoading(false)
            } else {
                showLoading(false)
            }
        }
    private val searchTelevisions =
        Observer<ArrayList<Television>> { television ->
            if (television != null) {
                mTvAdapter?.setListTelevision(television)
                showRecyclerList()
                showLoading(false)
                if (television.size === 0)
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show()
            } else {
                showLoading(false)
            }
        }
}
