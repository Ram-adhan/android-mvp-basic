package com.example.mvpapplication.data.network

import com.example.mvpapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpClient {
    val baseUrl = "http://makeup-api.herokuapp.com/api"
    val productEndpoint = "/v1/products.json"

    private val headerInterceptor: Interceptor = Interceptor {
        val req = it.request().newBuilder()

        req.addHeader("Content-Type", "application/json")

        return@Interceptor it.proceed(req.build())
    }

    val client: OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )
            .callTimeout(timeout = 15L, unit = TimeUnit.SECONDS)
            .connectTimeout(timeout = 20L, unit = TimeUnit.SECONDS)
            .build()
}