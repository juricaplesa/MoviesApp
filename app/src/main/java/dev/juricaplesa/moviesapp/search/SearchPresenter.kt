package dev.juricaplesa.moviesapp.search

import android.text.TextUtils
import dev.juricaplesa.moviesapp.api.ApiProvider
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Jurica Pleša
 */
class SearchPresenter constructor(
    private val apiProvider: ApiProvider,
    private val processScheduler: Scheduler,
    private val androidScheduler: Scheduler
) : SearchContract.Presenter {

    private lateinit var view: SearchContract.View

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun injectView(view: SearchContract.View) {
        this.view = view
    }

    override fun searchMovies(searchInput: String) {
        disposables.clear()
        if (!view.isActive()) {
            return
        }

        view.hideInitialMessage()
        view.hideEmptyMessage()

        if (TextUtils.isEmpty(searchInput) || searchInput.length < 3) {
            view.clearMovies()
            view.showInitialMessage()
            return
        }

        view.showLoadingIndicator()

        apiProvider.searchMovies(searchInput)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler, true)
                .subscribeBy(
                        onNext = {
                            if (view.isActive()) {
                                if (it.isSuccessful.toBoolean()) {
                                    view.setMovies(it.data)
                                } else {
                                    view.clearMovies()
                                    view.showEmptyMessage()
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

    override fun unsubscribe() {
        if (view.isActive()) {
            view.hideLoadingIndicator()
        }
        disposables.clear()
    }

}