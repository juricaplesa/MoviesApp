package dev.juricaplesa.moviesapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.juricaplesa.moviesapp.MainActivity
import dev.juricaplesa.moviesapp.details.DetailsModule
import dev.juricaplesa.moviesapp.search.SearchModule

/**
 * Created by Jurica Pleša
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [SearchModule::class, DetailsModule::class])
    abstract fun mainActivity(): MainActivity

}