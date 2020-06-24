package id.fiqridhan.movieapps.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.fiqridhan.movieapps.BuildConfig
import id.fiqridhan.movieapps.database.MovieHelper
import id.fiqridhan.movieapps.network.ApiClient
import id.fiqridhan.movieapps.network.ApiInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel(application: Application) :
    AndroidViewModel(application) {
    private var mApiInterface: ApiInterface? = null
    private val mMovieHelper: MovieHelper = MovieHelper.getInstance(application)!!
    private val listMovies =
        MutableLiveData<ArrayList<Movie>>()
    private val listNowPlayingMovies =
        MutableLiveData<ArrayList<Movie>>()
    private val listTelevisions =
        MutableLiveData<ArrayList<Television>>()
    private val listNowPlayingTv =
        MutableLiveData<ArrayList<Television>>()
    private val listFavoriteMovie =
        MutableLiveData<ArrayList<Movie>>()
    private val listFavoriteTelevision =
        MutableLiveData<ArrayList<Television>>()

    fun setMovie() {
        mApiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        try {
            val listItems = ArrayList<Movie>()
            mApiInterface?.getDiscoverMovie(BuildConfig.API_KEY, "en-US")?.enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    if (response.isSuccessful) {
                        try {
                            val responseObject = JSONObject(response.body())
                            val list = responseObject.getJSONArray("results")
                            for (i in 0 until list.length()) {
                                val movie = list.getJSONObject(i)
                                val movies = Movie(movie)
                                listItems.add(movies)
                            }
                            listMovies.postValue(listItems)
                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<String?>,
                    throwable: Throwable
                ) {
                }
            })
        } catch (e: Exception) {
            println("error$e")
        }
    }

    fun setNowPlayingMovie() {
        mApiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        try {
            val listItems = ArrayList<Movie>()
            mApiInterface?.getNowPlayingMovie(BuildConfig.API_KEY, "en-US")?.enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    if (response.isSuccessful) {
                        try {
                            val responseObject = JSONObject(response.body())
                            val list = responseObject.getJSONArray("results")
                            for (i in 0 until list.length()) {
                                val movie = list.getJSONObject(i)
                                val movies = Movie(movie)
                                listItems.add(movies)
                            }
                            listNowPlayingMovies.postValue(listItems)
                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<String?>,
                    throwable: Throwable
                ) {
                }
            })
        } catch (e: Exception) {
            println("error$e")
        }
    }

    fun setSearchMovie(query: String?) {
        mApiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        try {
            val listItems = ArrayList<Movie>()
            mApiInterface?.getSearchMovie(BuildConfig.API_KEY, "en-US", query)?.enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    if (response.isSuccessful) {
                        try {
                            val responseObject = JSONObject(response.body())
                            val list = responseObject.getJSONArray("results")
                            for (i in 0 until list.length()) {
                                val movie = list.getJSONObject(i)
                                val movies = Movie(movie)
                                listItems.add(movies)
                            }
                            listMovies.postValue(listItems)
                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<String?>,
                    throwable: Throwable
                ) {
                    Toast.makeText(
                        getApplication(),
                        "Something went wrong$throwable",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e: Exception) {
            println("error$e")
        }
    }

    fun setTelevision() {
        mApiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        try {
            val listItems = ArrayList<Television>()
            mApiInterface?.getDiscoverTv(BuildConfig.API_KEY, "en-US")?.enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    if (response.isSuccessful) {
                        try {
                            val responseObject = JSONObject(response.body())
                            val list = responseObject.getJSONArray("results")
                            for (i in 0 until list.length()) {
                                val television = list.getJSONObject(i)
                                val televisions = Television(television)
                                listItems.add(televisions)
                            }
                            listTelevisions.postValue(listItems)
                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<String?>,
                    throwable: Throwable
                ) {
                }
            })
        } catch (e: Exception) {
            println("error$e")
        }
    }

    fun setNowPlayingTelevision() {
        mApiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        try {
            val listItems = ArrayList<Television>()
            mApiInterface?.getNowPlayingTv(BuildConfig.API_KEY, "en-US")?.enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    if (response.isSuccessful) {
                        try {
                            val responseObject = JSONObject(response.body())
                            val list = responseObject.getJSONArray("results")
                            for (i in 0 until list.length()) {
                                val tv = list.getJSONObject(i)
                                val television = Television(tv)
                                listItems.add(television)
                            }
                            listNowPlayingTv.postValue(listItems)
                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<String?>,
                    throwable: Throwable
                ) {
                }
            })
        } catch (e: Exception) {
            println("error$e")
        }
    }

    fun setSearchTelevision(query: String?) {
        mApiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        try {
            val listItems = ArrayList<Television>()
            mApiInterface?.getSearchTelevision(BuildConfig.API_KEY, "en-US", query)?.enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    if (response.isSuccessful) {
                        try {
                            val responseObject = JSONObject(response.body())
                            val list = responseObject.getJSONArray("results")
                            for (i in 0 until list.length()) {
                                val television = list.getJSONObject(i)
                                val televisions = Television(television)
                                listItems.add(televisions)
                            }
                            listTelevisions.postValue(listItems)
                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<String?>,
                    throwable: Throwable
                ) {
                }
            })
        } catch (e: Exception) {
            println("error$e")
        }
    }

    fun setMovieDatabase(type: String?) {
        val movie: ArrayList<Movie>? = type?.let { mMovieHelper.getListFavoriteMovie(it) }
        listFavoriteMovie.postValue(movie)
    }

    fun setTelevisionDatabase(type: String?) {
        val television: ArrayList<Television>? =
            type?.let { mMovieHelper.getListFavoriteTelevision(it) }
        listFavoriteTelevision.postValue(television)
    }

    fun getMovieFavorite(type: String?): LiveData<ArrayList<Movie>> {
        setMovieDatabase(type)
        return listFavoriteMovie
    }

    fun getTelevisionFavorite(type: String?): LiveData<ArrayList<Television>> {
        setTelevisionDatabase(type)
        return listFavoriteTelevision
    }

    val movies: LiveData<ArrayList<Movie>>
        get() = listMovies

    val nowPlayingMovie: LiveData<ArrayList<Movie>>
        get() = listNowPlayingMovies

    val televisions: LiveData<ArrayList<Television>>
        get() = listTelevisions

    val nowPlayingTelevision: LiveData<ArrayList<Television>>
        get() = listNowPlayingTv

    val searchMovies: LiveData<ArrayList<Movie>>
        get() = listMovies

    val searchTv: LiveData<ArrayList<Television>>
        get() = listTelevisions

}
