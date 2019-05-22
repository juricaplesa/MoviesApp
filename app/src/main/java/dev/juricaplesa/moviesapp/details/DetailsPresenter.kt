package dev.juricaplesa.moviesapp.details

import dev.juricaplesa.moviesapp.api.ApiProvider
import dev.juricaplesa.moviesapp.api.MovieDetailsResponse
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Jurica Pleša
 */
class DetailsPresenter constructor(
        private val superheroApi: ApiProvider,
        private val mProcessScheduler: Scheduler,
        private val mAndroidScheduler: Scheduler
) : DetailsContract.Presenter {

    private lateinit var mView: DetailsContract.View

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun injectView(view: DetailsContract.View) {
        this.mView = view
    }

    override fun getMovieDetails(moviesImdbId: String) {
        mDisposables.clear()
        if (!mView.isActive()) {
            return
        }

        mView.showLoadingIndicator()

        superheroApi.getMovieDetails(moviesImdbId)
                .subscribeOn(mProcessScheduler)
                .observeOn(mAndroidScheduler, true)
                .subscribeBy(
                        onNext = {
                            if (mView.isActive()) {
                                if (it.isSuccessful.toBoolean()) {
                                    setMovieDetails(it)
                                } else {
                                    mView.showErrorMessage()
                                }
                                mView.hideLoadingIndicator()
                            }
                        },
                        onError = {
                            if (mView.isActive()) {
                                mView.showErrorMessage()
                                mView.hideLoadingIndicator()
                            }
                        }
                )
                .addTo(mDisposables)
    }

    private fun setMovieDetails(movie: MovieDetailsResponse) {
        if (!mView.isActive()) {
            return
        }

        mView.displayTitle(movie.title)
        mView.displayYear(movie.year)
        mView.displayGenre(movie.genre)
        mView.displayDirector(movie.director)
        mView.displayActors(movie.actors)
        mView.displayPlot(movie.plot)
        mView.displayPoster(movie.poster)
    }

    override fun unsubscribe() {

    }

}