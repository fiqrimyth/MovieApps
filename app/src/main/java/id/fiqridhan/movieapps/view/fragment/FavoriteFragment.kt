package id.fiqridhan.movieapps.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.adapter.ViewPageAdapter
import java.util.*

class FavoriteFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_favorite, container, false)
        val viewPager: ViewPager = rootView.findViewById(R.id.view_pager)
        viewPager.adapter = ViewPageAdapter(
            Objects.requireNonNull(
                childFragmentManager
            )
        )
        val tabLayout: TabLayout = rootView.findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
        Objects.requireNonNull(tabLayout.getTabAt(0))?.text = resources.getText(R.string.movie)
        Objects.requireNonNull(tabLayout.getTabAt(1))?.text = resources.getText(R.string.tv_show)
        return rootView
    }
}
