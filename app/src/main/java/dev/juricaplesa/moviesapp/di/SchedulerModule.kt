package dev.juricaplesa.moviesapp.di

import dagger.Module
import dagger.Provides
import dev.juricaplesa.moviesapp.common.ANDROID_SCHEDULER
import dev.juricaplesa.moviesapp.common.PROCESS_SCHEDULER
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

/**
 * Created by Jurica Pleša
 */
@Module
class SchedulerModule {

    @Provides
    @Named(PROCESS_SCHEDULER)
    fun provideProcessScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named(ANDROID_SCHEDULER)
    fun provideAndroidScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}