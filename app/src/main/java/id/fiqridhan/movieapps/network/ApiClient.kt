package id.fiqridhan.movieapps.network

import com.google.gson.GsonBuilder
import id.fiqridhan.movieapps.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiClient {
    private var sRetrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        if (sRetrofit == null) {
            sRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return sRetrofit
    }
}