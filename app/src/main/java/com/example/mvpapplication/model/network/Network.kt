package com.example.mvpapplication.model.network

import android.os.Build
import com.example.mvpapplication.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Network {
  var RAWG_API_KEY = ""

  val client: HttpClient by lazy {
    HttpClient(CIO) {
        install(Logging) {
          logger = Logger.ANDROID
          level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
        }
        install(ContentNegotiation) {
          json(
            Json {
              prettyPrint = true
              ignoreUnknownKeys = true
            }
          )
        }
        install(HttpTimeout) {
          connectTimeoutMillis = 10_000
          requestTimeoutMillis = 50_000
        }
        defaultRequest {
          headers {
            append(
              "Time-Zone",
              java.util.TimeZone.getDefault().getDisplayName(false, java.util.TimeZone.SHORT),
            )
          }
        }
      }
      .also {
        it.plugin(HttpSend).intercept { request ->
          // Add header with interceptor
          request.headers {
            append(
              "Device-Detail",
              "${Build.DEVICE}:${Build.MANUFACTURER}:${Build.MODEL}|android:${Build.VERSION.SDK_INT},${Build.VERSION.RELEASE}",
            )
          }
          execute(request)
        }
      }
  }
}

suspend fun HttpClient.postJson(
  urlString: String,
  block: HttpRequestBuilder.() -> Unit = {},
): HttpResponse = post {
  url(urlString)
  block().apply { contentType(ContentType.Application.Json) }
}
