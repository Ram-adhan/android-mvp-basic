package com.example.mvpapplication.data.network.api

import com.example.mvpapplication.data.model.AddUserModel
import com.example.mvpapplication.data.model.AddUserResponse
import com.example.mvpapplication.data.model.User
import com.example.mvpapplication.data.network.NetworkClient
import com.example.mvpapplication.data.network.ResponseStatus
import com.example.mvpapplication.data.network.getIntData
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ReqresApi {
    private val usersEndpoint = "/users"
    fun getUser(onResponse: (ResponseStatus<User>) -> Unit) {
        NetworkClient
            .client
            .newCall(
                NetworkClient.requestBuilder(usersEndpoint).build()
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
        val endpoint = "$usersEndpoint${if (pages > 1) "?page=$pages" else ""}"
        val request = NetworkClient.requestBuilder(endpoint).build()
        NetworkClient
            .client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(
                        ResponseStatus.Failed(
                            code = -1,
                            message = e.message.toString(),
                            throwable = e
                        )
                    )
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = JSONObject(response.body?.string() ?: "")
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = mapUsers(body),
                                method = "GET",
                                status = true
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

    fun addUser(data: AddUserModel, onResponse: (ResponseStatus<AddUserResponse>) -> Unit) {
        val requestBody = JSONObject()
            .put("name", data.name)
            .put("job", data.job)
            .toString()
        val request = NetworkClient
            .requestBuilder(usersEndpoint)
            .method("POST", requestBody.toRequestBody())
            .build()
        NetworkClient
            .client
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(
                        ResponseStatus.Failed(1, e.message.toString(), e)
                    )
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        onResponse.invoke(
                            ResponseStatus.Success(
                                AddUserResponse(
                                    JSONObject(
                                        response.body?.string().toString()
                                    )
                                )
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