package id.fiqridhan.movieapps.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import id.fiqridhan.movieapps.database.DatabaseContract.AUTHORITY
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.CONTENT_URI
import id.fiqridhan.movieapps.database.DatabaseContract.TABLE_MOVIE
import id.fiqridhan.movieapps.database.MovieHelper
import java.util.*

class MovieProvider : ContentProvider() {
    private var mMovieHelper: MovieHelper? = null

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(
                AUTHORITY,
                TABLE_MOVIE,
                MOVIE
            )
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_MOVIE/#",
                MOVIE_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        mMovieHelper = context?.let { MovieHelper.getInstance(it) }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        mMovieHelper?.open()
        return when (sUriMatcher.match(uri)) {
            MOVIE -> mMovieHelper?.queryProvider()
            MOVIE_ID -> uri.lastPathSegment?.let { mMovieHelper?.queryByIdProvider(it) }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun insert(
        uri: Uri,
        contentValues: ContentValues?
    ): Uri? {
        mMovieHelper?.open()
        val added: Long? = if (sUriMatcher.match(uri) == MOVIE) {
            mMovieHelper?.insertProvider(contentValues)
        } else {
            0
        }
        Objects.requireNonNull(context)
            ?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        mMovieHelper?.open()
        val updated: Int = (if (sUriMatcher.match(uri) == MOVIE_ID) {
            uri.lastPathSegment?.let { mMovieHelper?.updateProvider(it, contentValues) }
        } else {
            0
        }) as Int
        Objects.requireNonNull(context)
            ?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        mMovieHelper?.open()
        val deleted: Int = (if (sUriMatcher.match(uri) == MOVIE_ID) {
            uri.lastPathSegment?.let { mMovieHelper?.deleteProvider(it) }
        } else {
            0
        }) as Int
        Objects.requireNonNull(context)
            ?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}
