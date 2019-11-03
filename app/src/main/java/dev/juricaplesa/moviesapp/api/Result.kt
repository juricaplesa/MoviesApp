package dev.juricaplesa.moviesapp.api

/**
 * Created by Jurica Pleša
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

}