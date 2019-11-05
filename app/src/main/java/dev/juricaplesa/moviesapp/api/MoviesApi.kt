package dev.juricaplesa.moviesapp.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jurica Pleša
 */
interface MoviesApi {

    @GET(".")
    fun searchMovies(@Query("s") searchInput: String): Observable<SearchResponse>

    @GET(".")
    suspend fun getMovieDetails(@Query("i") imdbId: String): MovieDetailsResponse

}