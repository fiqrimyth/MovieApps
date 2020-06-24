package id.fiqridhan.movieapps.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.fiqridhan.movieapps.view.fragment.subfragment.MovieFavoriteFragment
import id.fiqridhan.movieapps.view.fragment.subfragment.TelevisionFavoriteFragment

class ViewPageAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val tabFragments: Array<Fragment> = arrayOf(
        MovieFavoriteFragment(),
        TelevisionFavoriteFragment()
    )

    override fun getItem(position: Int): Fragment {
        return tabFragments[position]
    }

    override fun getCount(): Int {
        return tabFragments.size
    }
}
