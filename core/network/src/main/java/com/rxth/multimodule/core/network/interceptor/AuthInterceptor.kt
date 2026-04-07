package com.rxth.multimodule.core.network.interceptor

import com.rxth.multimodule.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val originalHttpUrl = originalRequest.url

        val token = BuildConfig.ACCESS_TOKEN
        val apiKey = BuildConfig.API_KEY
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)

        val newRequest = originalRequest.newBuilder()
          //  .header("Authorization", "Bearer $token")
            .header("Accept", "application/json")
            .url(url.build())
            .build()

        return chain.proceed(newRequest)
    }
}