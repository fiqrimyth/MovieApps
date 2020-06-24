package id.fiqridhan.movieapps.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("discover/movie")
    fun getReleaseMovie(
        @Query("api_key") API_KEY: String?,
        @Query("primary_release_date.gte") ReleaseDate: String?,
        @Query("primary_release_date.lte") TodayDate: String?
    ): Call<String?>?

    @GET("discover/movie")
    fun getDiscoverMovie(
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?
    ): Call<String?>?

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?
    ): Call<String?>?

    @GET("search/movie")
    fun getSearchMovie(
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?,
        @Query("query") keyword: String?
    ): Call<String?>?

    @GET("discover/tv")
    fun getDiscoverTv(
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?
    ): Call<String?>?

    @GET("tv/on_the_air")
    fun getNowPlayingTv(
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?
    ): Call<String?>?

    @GET("search/tv")
    fun getSearchTelevision(
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?,
        @Query("query") keyword: String?
    ): Call<String?>?
}
