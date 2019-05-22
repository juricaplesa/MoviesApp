package dev.juricaplesa.moviesapp.search

import dev.juricaplesa.moviesapp.base.BasePresenter
import dev.juricaplesa.moviesapp.base.BaseView
import dev.juricaplesa.moviesapp.models.Movie

/**
 * Created by Jurica Pleša
 */
interface SearchContract {

    interface View : BaseView {
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showInitialMessage()

        fun hideInitialMessage()

        fun showEmptyMessage()

        fun hideEmptyMessage()

        fun showErrorMessage()

        fun setMovies(movies: List<Movie>)

        fun clearMovies()

    }

    interface Presenter : BasePresenter<View> {

        fun searchMovies(searchInput: String)

    }

}