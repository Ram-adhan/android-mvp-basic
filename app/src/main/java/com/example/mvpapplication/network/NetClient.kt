package com.example.mvpapplication.network

import com.example.mvpapplication.BuildConfig
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetClient {
    companion object {
        private val baseUrl = "https://reqres.in/api"
        fun getOkhttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) {
                                HttpLoggingInterceptor.Level.HEADERS
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                    }
                )
                .callTimeout(5, TimeUnit.SECONDS)
                .build()
        }

        private val headerInterceptor: Interceptor = Interceptor {
            val request = it.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()

            return@Interceptor it.proceed(request)
        }

        fun getApi(endpoint: String, body: JSONObject?, onResponse: (JSONObject?, throwable: Throwable?) -> Unit) {
            val getRequest = Request.Builder()
                .url("$baseUrl$endpoint")
            if (body != null) {
                getRequest.method("GET", body.toString().toRequestBody())
            }

            getOkhttpClient()
                .newCall(getRequest.build())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onResponse.invoke(null, e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val responseBody = response.body?.string()?.let { JSONObject(it) }
                            onResponse.invoke(responseBody, null)
                        } else {
                            onResponse.invoke(null, Throwable("failed response"))
                        }
                        response.body?.close()
                    }
                })
        }
    }
}