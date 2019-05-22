package dev.juricaplesa.moviesapp.details

import dev.juricaplesa.moviesapp.base.BasePresenter
import dev.juricaplesa.moviesapp.base.BaseView
import dev.juricaplesa.moviesapp.models.Movie

/**
 * Created by Jurica Pleša
 */
interface DetailsContract {

    interface View : BaseView {
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorMessage()

        fun displayTitle(title: String)

        fun displayYear(year: String)

        fun displayGenre(genre: String)

        fun displayDirector(director: String)

        fun displayActors(actors: String)

        fun displayPlot(plot: String)

        fun displayPoster(posterUrl: String)

    }

    interface Presenter : BasePresenter<View> {

        fun getMovieDetails(moviesImdbId: String)

    }

}