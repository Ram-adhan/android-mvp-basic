package com.example.mvpapplication.data.network

import com.example.mvpapplication.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetworkClient {
    companion object {
        const val BASE_URL = "https://masak-apa.tomorisakura.vercel.app/api"
        val client: OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                    }
                )
                .callTimeout(timeout = 5L, unit = TimeUnit.SECONDS)
                .connectTimeout(timeout = 2L, unit = TimeUnit.SECONDS)
                .build()

        fun requestBuilder(endpoint: String): Request = Request.Builder().url("$BASE_URL$endpoint").build()
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