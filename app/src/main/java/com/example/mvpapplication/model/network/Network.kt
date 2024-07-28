package com.example.mvpapplication.model.network

import com.example.mvpapplication.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Network {
    var RAWG_API_KEY = ""

    fun getRAWGUrl(endpoint: String) = BuildConfig.RAWG_URL + endpoint

    val client: HttpClient by lazy {
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.ANDROID
                level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
            }
            install(ContentNegotiation) { json(Json { prettyPrint = true }) }
            engine {
                requestTimeout = 2_000
                endpoint { connectTimeout = 1_000 }
            }
        }
    }
}
