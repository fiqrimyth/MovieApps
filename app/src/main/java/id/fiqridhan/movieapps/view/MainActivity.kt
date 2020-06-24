package id.fiqridhan.movieapps.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.view.fragment.FavoriteFragment
import id.fiqridhan.movieapps.view.fragment.MovieFragment
import id.fiqridhan.movieapps.view.fragment.TelevisionFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_movie
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    companion object;

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting_app) {
            val mIntent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragment: Fragment
                when (item.itemId) {
                    R.id.navigation_movie -> {
                        fragment = MovieFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment, fragment, fragment.javaClass.simpleName)
                            .commit()
                        return true
                    }
                    R.id.navigation_tv -> {
                        fragment = TelevisionFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment, fragment, fragment.javaClass.simpleName)
                            .commit()
                        return true
                    }
                    R.id.navigation_favorite -> {
                        fragment = FavoriteFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment, fragment, fragment.javaClass.simpleName)
                            .commit()
                        return true
                    }
                }
                return false
            }
        }
}