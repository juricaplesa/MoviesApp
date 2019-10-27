package dev.juricaplesa.moviesapp.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.juricaplesa.moviesapp.App
import dev.juricaplesa.moviesapp.common.API_KEY
import dev.juricaplesa.moviesapp.common.BASE_URL
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Jurica Pleša
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApiModule::class, SchedulerModule::class, ActivityBindingModule::class])
interface AppComponent: AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named(BASE_URL) baseUrl: String,
            @BindsInstance @Named(API_KEY) apiKey: String
        ): AppComponent
    }

}