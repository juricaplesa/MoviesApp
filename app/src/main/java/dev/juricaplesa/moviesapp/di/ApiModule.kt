package dev.juricaplesa.moviesapp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dev.juricaplesa.moviesapp.api.MoviesApi
import dev.juricaplesa.moviesapp.api.interceptors.MoviesApiKeyInterceptor
import dev.juricaplesa.moviesapp.common.API_KEY
import dev.juricaplesa.moviesapp.common.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Jurica Pleša
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(@Named(BASE_URL) baseUrl: String, client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(@Named(API_KEY) apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(MoviesApiKeyInterceptor(apiKey))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

}