package dev.juricaplesa.moviesapp.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Jurica Pleša
 */
class MoviesApiKeyInterceptor(apiKey: String) : Interceptor {

    private var mApiKey: String = apiKey

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        //Skip if request contains skip key
        val headerValue = originalRequest.header(SKIP_KEY)
        headerValue?.let { return chain.proceed(originalRequest) }

        val originalHttpUrl = originalRequest.url()
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", mApiKey)
                .build()

        val requestBuilder = originalRequest.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private const val SKIP_KEY = "skip-new-api-key-query"
    }
}