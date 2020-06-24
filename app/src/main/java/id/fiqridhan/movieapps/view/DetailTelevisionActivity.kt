package id.fiqridhan.movieapps.view


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.database.MovieHelper
import id.fiqridhan.movieapps.model.Television
import id.fiqridhan.movieapps.widget.MovieFavoriteWidget
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.String
import java.util.*

class DetailTelevisionActivity(private var mTelevision: Television) : AppCompatActivity(),
    View.OnClickListener {
    private var mMovieHelper: MovieHelper? = null
    private var isFavorite = false

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvName = findViewById<TextView>(R.id.text_name)
        val tvRelease = findViewById<TextView>(R.id.text_release)
        val tvDesc = findViewById<TextView>(R.id.text_description)
        val tvPopularity = findViewById<TextView>(R.id.text_popularity)
        val tvVoteAverage = findViewById<TextView>(R.id.text_vote_average)
        val imgPhoto = findViewById<ImageView>(R.id.image_item_photo)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) supportActionBar!!.setTitle("Detail Television")
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        Objects.requireNonNull(supportActionBar)?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24dp)
        button_favorite.setOnClickListener(this)

        mTelevision = intent.getParcelableExtra(EXTRA_TV)

        tvName.text = mTelevision.getmName()
        tvRelease.text = mTelevision.getmRelease()
        tvDesc.text = mTelevision.getmDesc()
        tvPopularity.text = mTelevision.getmPopularity()
        tvVoteAverage.text = mTelevision.getmVote()

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w780/" + mTelevision.getmPhoto())
            .placeholder(R.drawable.ic_image_black_24dp)
            .transform(FitCenter())
            .into(imgPhoto)

        mMovieHelper = MovieHelper.getInstance(applicationContext)
        if (mMovieHelper?.CheckMovie(String.valueOf(mTelevision.getmId()))!!) {
            button_favorite.background = resources.getDrawable(R.drawable.ic_favorite_24dp)
            isFavorite = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_favorite) if (!isFavorite) {
            mTelevision?.setmType("television")
            mMovieHelper?.open()
            val result: Long =
                (mMovieHelper?.insertTelevision(mTelevision) ?: mMovieHelper?.close()) as Long
            if (result > 0) {
                button_favorite?.background = resources.getDrawable(R.drawable.ic_favorite_24dp)
                Toast.makeText(
                    applicationContext,
                    "Success " + mTelevision?.getmName().toString() + " Added to Favorite",
                    Toast.LENGTH_SHORT
                ).show()
                updateWidget()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Failed " + mTelevision.getmName().toString() + " Added to Favorite",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            mTelevision?.setmType("television")
            mMovieHelper?.open()
            val result: Int? = mMovieHelper?.deleteTelevision(mTelevision.getmId())
            mMovieHelper?.close()
            if (result != null) {
                if (result > 0) {
                    button_favorite.background =
                        resources.getDrawable(R.drawable.ic_favorite_border_24dp)
                    Toast.makeText(
                        applicationContext,
                        "Success " + mTelevision.getmName().toString() + " Delete from Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateWidget()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Failed " + mTelevision.getmName().toString() + " Delete Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateWidget() {
        val widgetUpdateIntent = Intent(this, MovieFavoriteWidget::class.java)
        widgetUpdateIntent.action = MovieFavoriteWidget.TOAST_ACTION
        sendBroadcast(widgetUpdateIntent)
    }

    companion object {
        const val EXTRA_TV = "extra_tv"
    }
}