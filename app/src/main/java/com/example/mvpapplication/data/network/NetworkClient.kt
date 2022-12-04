package com.example.mvpapplication.data.network

import com.example.mvpapplication.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetworkClient {
    companion object {
        const val BASE_URL = "https://reqres.in/api"
        private val headerInterceptor: Interceptor = Interceptor {
            val request = it.request().newBuilder()
            request
                .addHeader("Content-Type", "application/json")

            return@Interceptor it.proceed(request.build())

        }

        val client: OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                    }
                )
                .callTimeout(timeout = 5L, unit = TimeUnit.SECONDS)
                .connectTimeout(timeout = 2L, unit = TimeUnit.SECONDS)
                .build()

        fun requestBuilder(endpoint: String): Request.Builder =
            Request
                .Builder()
                .url("$BASE_URL$endpoint")
    }

    fun getAsync(endpoint: String, onSuccess: (Response) -> Unit) {
        val request = Request
            .Builder()
            .url("$BASE_URL$endpoint")
            .build()

        client.newCall(request = request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

            override fun onResponse(call: Call, response: Response) {
                onSuccess.invoke(response)
            }
        })
    }

    fun getSync(endpoint: String): Response {
        val request = Request
            .Builder()
            .url("$BASE_URL$endpoint")
            .build()

        return client.newCall(request).execute()
    }

}