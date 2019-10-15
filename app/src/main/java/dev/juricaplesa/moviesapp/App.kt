package dev.juricaplesa.moviesapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.juricaplesa.moviesapp.di.DaggerAppComponent

/**
 * Created by Jurica Pleša
 */
class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(getString(R.string.movies_api_base_url), getString(R.string.movies_api_key))
    }

}