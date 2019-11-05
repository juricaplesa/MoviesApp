package dev.juricaplesa.moviesapp.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.juricaplesa.moviesapp.api.interceptors.MoviesApiKeyInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Jurica Pleša
 */
class ApiProvider(
    baseUrl: String,
    apiKey: String
) {

    private var moviesApi: MoviesApi

    companion object {
        private var instance: ApiProvider? = null

        @JvmStatic
        fun getInstance(baseUrl: String, apiKey: String) = instance
            ?: synchronized(ApiProvider::class.java) {
                instance ?: ApiProvider(baseUrl, apiKey)
                    .also { instance = it }
            }
    }

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(MoviesApiKeyInterceptor(apiKey))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    fun searchMovies(searchInput: String): Observable<SearchResponse> {
        return moviesApi.searchMovies(searchInput)
    }

    suspend fun getMovieDetails(imdbId: String): Result<MovieDetailsResponse> {
        return try {
            val result = moviesApi.getMovieDetails(imdbId)
            Result.Success(result)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

}