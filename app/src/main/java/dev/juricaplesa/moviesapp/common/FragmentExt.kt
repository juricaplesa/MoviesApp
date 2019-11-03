package dev.juricaplesa.moviesapp.common

import androidx.fragment.app.Fragment
import dev.juricaplesa.moviesapp.App
import dev.juricaplesa.moviesapp.ViewModelFactory

/**
 * Created by Jurica Pleša
 */
fun Fragment.getViewModelFactory(): ViewModelFactory {
    val apiProvider = App.apiProvider
    return ViewModelFactory(apiProvider)
}