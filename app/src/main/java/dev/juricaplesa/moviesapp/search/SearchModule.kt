package dev.juricaplesa.moviesapp.search

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Jurica Pleša
 */
@Module
abstract class SearchModule {

    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment

    @Binds
    abstract fun searchPresenter(searchPresenter: SearchPresenter): SearchContract.Presenter

}