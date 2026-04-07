package com.rxth.multimodule.core.network

import com.rxth.multimodule.core.network.constance.NetworkConstance
import com.rxth.multimodule.core.network.constance.TimeOutConstance
import com.rxth.multimodule.core.network.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

val networkModule = module {
    single(named(NetworkConstance.DEFAULT_CLIENT)) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TimeOutConstance.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TimeOutConstance.WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TimeOutConstance.READ_TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TimeOutConstance.CALL_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    single(named(NetworkConstance.DEFAULT_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get(named(NetworkConstance.DEFAULT_CLIENT)))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
