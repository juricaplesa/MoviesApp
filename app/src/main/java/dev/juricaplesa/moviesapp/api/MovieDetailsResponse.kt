package dev.juricaplesa.moviesapp.api

import com.squareup.moshi.Json
import dev.juricaplesa.moviesapp.models.Movie

/**
 * Created by Jurica Pleša
 */
data class MovieDetailsResponse(

    @Json(name = "Response")
    val isSuccessful: String

) : Movie()