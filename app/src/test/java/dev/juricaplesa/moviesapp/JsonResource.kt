package dev.juricaplesa.moviesapp

import okio.Okio

/**
 * Created by Jurica Pleša
 */
object JsonResource {
    fun fromResource(file: String): String {
        val classLoader = Thread.currentThread().contextClassLoader
        classLoader.getResourceAsStream(file).use { `is` -> return Okio.buffer(Okio.source(`is`)).readUtf8() }
    }
}