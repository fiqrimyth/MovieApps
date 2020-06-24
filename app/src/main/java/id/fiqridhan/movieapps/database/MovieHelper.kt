package id.fiqridhan.movieapps.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.MOVIE_ID
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.OVERVIEW
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.POPULARITY
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.RELEASE_DATE
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.TELEVISION_ID
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.TITLE
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.TYPE
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.URL_IMAGE
import id.fiqridhan.movieapps.database.DatabaseContract.MovieColumns.VOTE_AVERAGE
import id.fiqridhan.movieapps.database.DatabaseContract.TABLE_MOVIE
import id.fiqridhan.movieapps.model.Movie
import id.fiqridhan.movieapps.model.Television
import java.util.*

class MovieHelper private constructor(context: Context) {
    @Throws(SQLException::class)
    fun open() {
        sDatabase =
            sDataBaseHelper.getWritableDatabase()
    }

    fun close() {
        sDataBaseHelper.close()
        if (sDatabase!!.isOpen) sDatabase!!.close()
    }

    fun getListFavoriteMovie(type: String): ArrayList<Movie> {
        val arrayList: ArrayList<Movie> = ArrayList<Movie>()
        sDatabase =
            sDataBaseHelper.readableDatabase
        val cursor = sDatabase!!.query(
            DATABASE_TABLE,
            arrayOf(
                BaseColumns._ID,
                TITLE,
                TYPE,
                OVERVIEW,
                POPULARITY,
                RELEASE_DATE,
                URL_IMAGE,
                VOTE_AVERAGE
            ),
            "$TYPE=?",
            arrayOf(type),
            null,
            null,
            BaseColumns._ID + " ASC",
            null
        )
        cursor.moveToFirst()
        var movie: Movie
        if (cursor.count > 0) {
            do {
                movie = Movie()
                movie.setmId(cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)))
                movie.setmName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)))
                movie.setmType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)))
                movie.setmDesc(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)))
                movie.setmPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)))
                movie.setmRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)))
                movie.setmPhoto(cursor.getString(cursor.getColumnIndexOrThrow(URL_IMAGE)))
                movie.setmVote(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)))
                arrayList.add(movie)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun getListFavoriteTelevision(type: String): ArrayList<Television> {
        val arrayList: ArrayList<Television> = ArrayList<Television>()
        sDatabase =
            sDataBaseHelper.readableDatabase
        val cursor = sDatabase!!.query(
            DATABASE_TABLE,
            arrayOf(
                BaseColumns._ID,
                TITLE,
                TYPE,
                OVERVIEW,
                POPULARITY,
                RELEASE_DATE,
                URL_IMAGE,
                VOTE_AVERAGE
            ),
            "$TYPE=?",
            arrayOf(type),
            null,
            null,
            BaseColumns._ID + " ASC",
            null
        )
        cursor.moveToFirst()
        var television: Television
        if (cursor.count > 0) {
            do {
                television = Television()
                television.setmId(cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)))
                television.setmName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)))
                television.setmType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)))
                television.setmDesc(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)))
                television.setmPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)))
                television.setmRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)))
                television.setmPhoto(cursor.getString(cursor.getColumnIndexOrThrow(URL_IMAGE)))
                television.setmVote(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)))
                arrayList.add(television)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun insertMovie(movie: Movie): Long {
        val args = ContentValues().also {
            it.put(BaseColumns._ID, movie.getmId())
            it.put(MOVIE_ID, movie.getmId())
            it.put(TITLE, movie.getmName())
            it.put(TYPE, movie.getmType())
            it.put(OVERVIEW, movie.getmDesc())
            it.put(POPULARITY, movie.getmPopularity())
            it.put(RELEASE_DATE, movie.getmRelease())
            it.put(URL_IMAGE, movie.getmPhoto())
            it.put(VOTE_AVERAGE, movie.getmVote())
        }
        return sDatabase!!.insert(
            DATABASE_TABLE,
            null,
            args
        )
    }

    fun insertTelevision(television: Television): Long {
        val args = ContentValues().also {
            it.put(BaseColumns._ID, television.getmId())
            it.put(TELEVISION_ID, television.getmId())
            it.put(TITLE, television.getmName())
            it.put(TYPE, television.getmType())
            it.put(OVERVIEW, television.getmDesc())
            it.put(POPULARITY, television.getmPopularity())
            it.put(RELEASE_DATE, television.getmRelease())
            it.put(URL_IMAGE, television.getmPhoto())
            it.put(VOTE_AVERAGE, television.getmVote())
        }
        return sDatabase!!.insert(
            DATABASE_TABLE,
            null,
            args
        )
    }

    @Throws(SQLException::class)
    fun CheckMovie(id: String): Boolean {
        var isFavorite = false
        sDatabase =
            sDataBaseHelper.getReadableDatabase()
        val cursor = sDatabase!!.rawQuery(
            "SELECT * from " + DATABASE_TABLE
                    + " where " + MOVIE_ID + "=?", arrayOf(id)
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                isFavorite = true
            } while (cursor.moveToNext())
        }
        assert(cursor != null)
        cursor!!.close()
        return isFavorite
    }

    fun deleteMovie(id: Int): Int {
        return sDatabase!!.delete(
            DATABASE_TABLE,
            BaseColumns._ID + " = '" + id + "'",
            null
        )
    }

    fun deleteTelevision(id: Int): Int {
        return sDatabase!!.delete(
            DATABASE_TABLE,
            BaseColumns._ID + " = '" + id + "'",
            null
        )
    }

    fun queryByIdProvider(id: String): Cursor {
        return sDatabase!!.query(
            DATABASE_TABLE, null
            , BaseColumns._ID + " = ?"
            , arrayOf(id), null
            , null
            , null
            , null
        )
    }

    fun queryProvider(): Cursor {
        return sDatabase!!.query(
            DATABASE_TABLE
            , null
            , null
            , null
            , null
            , null
            , BaseColumns._ID + " ASC"
        )
    }

    fun insertProvider(values: ContentValues?): Long {
        return sDatabase!!.insert(
            DATABASE_TABLE,
            null,
            values
        )
    }

    fun updateProvider(id: String, values: ContentValues?): Int {
        return sDatabase!!.update(
            DATABASE_TABLE,
            values,
            BaseColumns._ID + " = ?",
            arrayOf(id)
        )
    }

    fun deleteProvider(id: String): Int {
        return sDatabase!!.delete(
            DATABASE_TABLE,
            BaseColumns._ID + " = ?",
            arrayOf(id)
        )
    }

    companion object {
        const val DATABASE_TABLE: String = TABLE_MOVIE
        private lateinit var sDataBaseHelper: DatabaseHelper
        private var sInstance: MovieHelper? = null
        private var sDatabase: SQLiteDatabase? = null
        fun getInstance(context: Context): MovieHelper? {
            if (sInstance == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (sInstance == null) {
                        sInstance =
                            MovieHelper(context)
                    }
                }
            }
            return sInstance
        }
    }

    init {
        sDataBaseHelper = DatabaseHelper(context)
    }
}