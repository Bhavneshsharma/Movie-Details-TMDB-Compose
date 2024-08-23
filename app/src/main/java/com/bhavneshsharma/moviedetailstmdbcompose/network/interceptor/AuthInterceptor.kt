package com.bhavneshsharma.moviedetailstmdbcompose.network.interceptor

import com.bhavneshsharma.moviedetailstmdbcompose.BuildConfig
import com.bhavneshsharma.moviedetailstmdbcompose.R
import com.bhavneshsharma.moviedetailstmdbcompose.utils.ResourcesProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter(resourcesProvider.getString(R.string.api_key), BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .header(
                resourcesProvider.getString(R.string.authorization),
                resourcesProvider.getString(R.string.bearer) + BuildConfig.READ_ACCESS_TOKEN
            )
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}