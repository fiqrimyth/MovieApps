package id.fiqridhan.movieapps.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.database.MovieHelper
import id.fiqridhan.movieapps.model.Movie
import java.util.*
import java.util.concurrent.ExecutionException

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsFactory {
    private val mMovieHelper: MovieHelper = MovieHelper.getInstance(mContext)!!
    private var listMovies: ArrayList<Movie> = ArrayList<Movie>()
    override fun onCreate() {}
    override fun onDataSetChanged() {
        listMovies = mMovieHelper.getListFavoriteMovie("movie")
    }

    override fun onDestroy() {}
    override fun getCount(): Int {
        return listMovies.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        val bmp: Bitmap
        try {
            bmp = Glide.with(mContext)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w500/" + listMovies[position].getmPhoto())
                .into(
                    Target.SIZE_ORIGINAL,
                    Target.SIZE_ORIGINAL
                )
                .get()
            rv.setImageViewBitmap(R.id.imageView, bmp)
            rv.setTextViewText(R.id.textView_widget, listMovies[position].getmName())
            Log.d("Widget", "Success")
        } catch (e: InterruptedException) {
            Log.d("Widget Load Error", "Error")
        } catch (e: ExecutionException) {
            Log.d("Widget Load Error", "Error")
        }
        val extras = Bundle()
        extras.putInt(MovieFavoriteWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

}
