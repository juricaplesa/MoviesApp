package dev.juricaplesa.moviesapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.juricaplesa.moviesapp.R
import dev.juricaplesa.moviesapp.api.ApiProvider
import dev.juricaplesa.moviesapp.api.Result
import dev.juricaplesa.moviesapp.models.Movie
import kotlinx.coroutines.launch

/**
 * Created by Jurica Pleša
 */
class DetailsViewModel(
    private val apiProvider: ApiProvider
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _toastMessage = MutableLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    fun getMovieDetails(moviesImdbId: String) {
        _dataLoading.value = true

        viewModelScope.launch {

            apiProvider.getMovieDetails(moviesImdbId).let { result ->
                if (result is Result.Success) {
                    if (result.data.isSuccessful.toBoolean()) {
                        _movie.value = result.data
                    } else {
                        _toastMessage.value = R.string.common_error_message
                    }
                } else {
                    _toastMessage.value = R.string.common_error_message
                }
                _dataLoading.value = false
            }
        }
    }

}