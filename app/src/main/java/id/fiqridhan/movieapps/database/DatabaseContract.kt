package id.fiqridhan.movieapps.database

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val TABLE_MOVIE = "movie"
    const val AUTHORITY = "com.smk.informatics"
    private const val SCHEME = "content"
    fun getColumnString(
        cursor: Cursor,
        columnName: String?
    ): String {
        return cursor.getString(cursor.getColumnIndex(columnName))
    }

    fun getColumnInt(cursor: Cursor, columnName: String?): Int {
        return cursor.getInt(cursor.getColumnIndex(columnName))
    }

    object MovieColumns : BaseColumns {
        const val _ID = "id"
        const val MOVIE_ID = "id"
        const val TELEVISION_ID = "id"
        const val TYPE = "type"
        const val OVERVIEW = "overview"
        const val RELEASE_DATE = "release_date"
        const val TITLE = "title"
        const val URL_IMAGE = "poster_path"
        const val VOTE_AVERAGE = "vote_average"
        const val POPULARITY = "popularity"
        val CONTENT_URI = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build()!!
    }
}