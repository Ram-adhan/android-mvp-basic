package com.example.mvpapplication.model.services

import com.example.mvpapplication.BuildConfig
import com.example.mvpapplication.model.dto.Detail
import com.example.mvpapplication.model.dto.RestDevObject
import com.example.mvpapplication.model.dto.RestDevObjectRequest
import com.example.mvpapplication.model.network.ResponseFailure
import com.example.mvpapplication.model.network.postJson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class RESTfulDevService(private val client: HttpClient) {
  companion object {
    const val OBJECTS = "objects"

    fun buildUrl(endpoint: String): String = BuildConfig.RESTFUL_DEV_URL + endpoint
  }

  suspend fun getAllObjects(): Result<List<RestDevObject>> {
    return try {
      val response = client.get(buildUrl(OBJECTS))
      val result =
        if (response.status.isSuccess()) {
          Result.success(response.body<List<RestDevObject>>())
        } else {
          Result.failure(
            ResponseFailure(
              code = response.status.value.toString(),
              message = response.bodyAsText(),
            )
          )
        }
      result
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  suspend fun addObject(item: RestDevObjectRequest<Detail>): Result<RestDevObject> {
    return try {
      val response = client.postJson(buildUrl(OBJECTS)) { setBody(item) }
      val result =
        if (response.status.isSuccess()) {
          Result.success(response.body<RestDevObject>())
        } else {
          Result.failure(
            ResponseFailure(
              code = response.status.value.toString(),
              message = response.bodyAsText(),
            )
          )
        }
      result
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
