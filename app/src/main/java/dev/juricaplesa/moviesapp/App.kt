package dev.juricaplesa.moviesapp

import android.app.Application
import dev.juricaplesa.moviesapp.api.ApiProvider

/**
 * Created by Jurica Pleša
 */
class App : Application() {

    companion object {
        lateinit var apiProvider: ApiProvider
            private set
    }

    override fun onCreate() {
        super.onCreate()
        apiProvider = ApiProvider.getInstance(getString(R.string.movies_api_base_url), getString(R.string.movies_api_key))
    }

}