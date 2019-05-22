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
        private val superheroApi: ApiProvider,
        private val mProcessScheduler: Scheduler,
        private val mAndroidScheduler: Scheduler
) : SearchContract.Presenter {

    private lateinit var mView: SearchContract.View

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun injectView(view: SearchContract.View) {
        this.mView = view
    }

    override fun searchMovies(searchInput: String) {
        mDisposables.clear()
        if (!mView.isActive()) {
            return
        }

        mView.hideInitialMessage()
        mView.hideEmptyMessage()

        if (TextUtils.isEmpty(searchInput) || searchInput.length < 3) {
            mView.clearMovies()
            mView.showInitialMessage()
            return
        }

        mView.showLoadingIndicator()

        superheroApi.searchMovies(searchInput)
                .subscribeOn(mProcessScheduler)
                .observeOn(mAndroidScheduler, true)
                .subscribeBy(
                        onNext = {
                            if (mView.isActive()) {
                                if (it.isSuccessful.toBoolean()) {
                                    mView.setMovies(it.data)
                                } else {
                                    mView.clearMovies()
                                    mView.showEmptyMessage()
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

    override fun unsubscribe() {
        if (mView.isActive()) {
            mView.hideLoadingIndicator()
        }
        mDisposables.clear()
    }

}