package dev.juricaplesa.moviesapp.models

import com.squareup.moshi.Json

/**
 * Created by Jurica Pleša
 */
open class Movie {

    @Json(name = "imdbID")
    var imdbId: String = ""
    @Json(name = "Title")
    var title: String = ""
    @Json(name = "Year")
    var year: String = ""
    @Json(name = "Genre")
    var genre: String = ""
    @Json(name = "Director")
    var director: String = ""
    @Json(name = "Actors")
    var actors: String = ""
    @Json(name = "Plot")
    var plot: String = ""
    @Json(name = "Poster")
    var poster: String = ""

}