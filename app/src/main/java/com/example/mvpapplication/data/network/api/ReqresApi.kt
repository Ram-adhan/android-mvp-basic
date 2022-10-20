package com.example.mvpapplication.data.network.api

import com.example.mvpapplication.data.model.User
import com.example.mvpapplication.data.network.NetworkClient
import com.example.mvpapplication.data.network.ResponseStatus
import com.example.mvpapplication.data.network.getIntData
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ReqresApi {
    fun getUser(onResponse: (ResponseStatus<User>) -> Unit) {
        val endpoint = "/users"
        NetworkClient
            .client
            .newCall(
                NetworkClient.requestBuilder(endpoint)
            )
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(ResponseStatus.Failed(1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = JSONObject(response.body?.string() ?: "")
                        onResponse.invoke(
                            ResponseStatus.Success(User(body))
                        )
                    } else {
                        onResponse.invoke(
                            ResponseStatus.Failed(response.code, "Failed")
                        )
                    }
                }
            })
    }

    fun getUserPagination(pages: Int = 1, onResponse: (ResponseStatus<List<User>>) -> Unit) {
        val endpoint = "/users${if (pages > 1) pages else ""}"
        val request = NetworkClient.requestBuilder(endpoint)
        NetworkClient
            .client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(ResponseStatus.Failed(1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = JSONObject(response.body?.string() ?: "")
                        onResponse.invoke(
                            ResponseStatus.SuccessPagination(
                                page = body.getIntData("page"),
                                perPage = body.getIntData("per_page"),
                                total = body.getIntData("total"),
                                totalPages = body.getIntData("total_pages"),
                                data = mapUsers(body)
                            )
                        )
                    } else {
                        onResponse.invoke(
                            ResponseStatus.Failed(response.code, "Failed")
                        )
                    }
                }
            })
    }

    private fun mapUsers(jsonObject: JSONObject): List<User> {
        val usersArray = jsonObject.getJSONArray("data")
        val resultData = mutableListOf<User>()
        for (i in 0 until usersArray.length()) {
            val obj = usersArray.getJSONObject(i)
            resultData.add(User(obj))
        }
        return resultData
    }
}