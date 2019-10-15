package dev.juricaplesa.moviesapp.details

import android.text.TextUtils
import dev.juricaplesa.moviesapp.api.MovieDetailsResponse
import dev.juricaplesa.moviesapp.api.MoviesApi
import dev.juricaplesa.moviesapp.common.ANDROID_SCHEDULER
import dev.juricaplesa.moviesapp.common.PROCESS_SCHEDULER
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Jurica Pleša
 */
class DetailsPresenter @Inject constructor(
    private val moviesApi: MoviesApi,
    @Named(PROCESS_SCHEDULER) private val processScheduler: Scheduler,
    @Named(ANDROID_SCHEDULER) private val androidScheduler: Scheduler
) : DetailsContract.Presenter {

    private lateinit var view: DetailsContract.View

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun injectView(view: DetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(moviesImdbId: String) {
        disposables.clear()
        if (!view.isActive()) {
            return
        }

        if (TextUtils.isEmpty(moviesImdbId)) {
            view.showErrorMessage()
            return
        }

        view.showLoadingIndicator()

        moviesApi.getMovieDetails(moviesImdbId)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler, true)
                .subscribeBy(
                        onNext = {
                            if (view.isActive()) {
                                if (it.isSuccessful.toBoolean()) {
                                    setMovieDetails(it)
                                } else {
                                    view.showErrorMessage()
                                }
                                view.hideLoadingIndicator()
                            }
                        },
                        onError = {
                            if (view.isActive()) {
                                view.showErrorMessage()
                                view.hideLoadingIndicator()
                            }
                        }
                )
                .addTo(disposables)
    }

    private fun setMovieDetails(movie: MovieDetailsResponse) {
        if (!view.isActive()) {
            return
        }

        view.displayTitle(movie.title)
        view.displayYear(movie.year)
        view.displayGenre(movie.genre)
        view.displayDirector(movie.director)
        view.displayActors(movie.actors)
        view.displayPlot(movie.plot)
        view.displayPoster(movie.poster)
    }

    override fun unsubscribe() {
        if (view.isActive()) {
            view.hideLoadingIndicator()
        }
        disposables.clear()
    }

}