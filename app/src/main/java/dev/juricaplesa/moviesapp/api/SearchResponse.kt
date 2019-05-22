package dev.juricaplesa.moviesapp.api

import com.squareup.moshi.Json
import dev.juricaplesa.moviesapp.models.Movie

/**
 * Created by Jurica Pleša
 */
data class SearchResponse (

    @Json(name = "totalResults")
    val totalResults: Int = 0,
    @Json(name = "Response")
    val isSuccessful: String,
    @Json(name = "Search")
    val data: List<Movie> = emptyList()

)