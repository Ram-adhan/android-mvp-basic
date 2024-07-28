package com.example.mvpapplication.model.services

import com.example.mvpapplication.BuildConfig
import com.example.mvpapplication.model.dto.RestDevObject
import com.example.mvpapplication.model.network.Network
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

class RESTfulDevService(private val client: HttpClient) {
    companion object {
        const val OBJECTS = "objects"
    }

    suspend fun getAllObjects(): List<RestDevObject> {
        var result = emptyList<RestDevObject>()
        val response = client.get(OBJECTS)
        if (response.status.isSuccess()) {
            result = response.body<List<RestDevObject>>()
        }
        return result
    }
}
