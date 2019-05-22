package dev.juricaplesa.moviesapp.base

/**
 * Created by Jurica Pleša
 */
interface BasePresenter<T> {

    fun injectView(view: T)

    fun unsubscribe()

}