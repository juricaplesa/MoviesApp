package dev.juricaplesa.moviesapp.details

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Jurica Pleša
 */
@Module
abstract class DetailsModule {

    @ContributesAndroidInjector
    abstract fun detailsFragment(): DetailsFragment

    @Binds
    abstract fun detailsPresenter(detailsPresenter: DetailsPresenter): DetailsContract.Presenter

}