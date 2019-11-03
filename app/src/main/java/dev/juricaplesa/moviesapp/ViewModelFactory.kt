package dev.juricaplesa.moviesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.juricaplesa.moviesapp.api.ApiProvider
import dev.juricaplesa.moviesapp.details.DetailsViewModel

/**
 * Created by Jurica Pleša
 */
class ViewModelFactory constructor(
    private val apiProvider: ApiProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(DetailsViewModel::class.java) -> DetailsViewModel(apiProvider)
                else -> throw IllegalArgumentException("Unknown ViewModel")
            }
        } as T

}
