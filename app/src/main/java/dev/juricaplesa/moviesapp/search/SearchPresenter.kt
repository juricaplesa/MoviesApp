package dev.juricaplesa.moviesapp.search

import android.text.TextUtils
import com.jakewharton.rxbinding2.InitialValueObservable
import dev.juricaplesa.moviesapp.api.MoviesApi
import dev.juricaplesa.moviesapp.common.ANDROID_SCHEDULER
import dev.juricaplesa.moviesapp.common.PROCESS_SCHEDULER
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Jurica Pleša
 */
class SearchPresenter @Inject constructor(
    private val moviesApi: MoviesApi,
    @Named(PROCESS_SCHEDULER) private val processScheduler: Scheduler,
    @Named(ANDROID_SCHEDULER) private val androidScheduler: Scheduler
) : SearchContract.Presenter {

    private lateinit var view: SearchContract.View

    private val searchDisposable: CompositeDisposable = CompositeDisposable()
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun injectView(view: SearchContract.View) {
        this.view = view
    }

    override fun startObservingSearchTextChanges(searchTextChangesObservable: InitialValueObservable<CharSequence>) {
        searchTextChangesObservable.skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(androidScheduler)
            .subscribeBy(
                onNext = {
                    searchMovies(it.toString())
                }
            )
            .addTo(searchDisposable)
    }

    fun searchMovies(searchInput: String) {
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

        moviesApi.searchMovies(searchInput)
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
        searchDisposable.clear()
    }

}