package com.example.mvpapplication.data.network.api

import com.example.mvpapplication.data.model.AddUserModel
import com.example.mvpapplication.data.model.AddUserResponse
import com.example.mvpapplication.data.model.User
import com.example.mvpapplication.data.model.UserPagination
import com.example.mvpapplication.data.network.*
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
                NetworkClient.requestBuilder(usersEndpoint)
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
        val request = NetworkClient.requestBuilder(endpoint)
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
                        val userPagination = deserializeJson<UserPagination>(response.body?.string() ?: "") ?: UserPagination()
                        val adapter = MoshiExtension.moshi.adapter(UserPagination::class.java)
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = userPagination.data,
                                method = "GET",
                                status = true
                            )
                        )
                    } else {
                        onResponse.invoke(
                            ResponseStatus.Failed(response.code, "Failed")
                        )
                    }
                    response.body?.close()
                }
            })
    }

    fun getError(onResponse: (ResponseStatus<Nothing>) -> Unit) {
        NetworkClient
            .client
            .newCall(NetworkClient.requestBuilder("/unknown/23"))
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(ResponseStatus.Failed(-1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    onResponse.invoke(ResponseStatus.Failed(-1, response.message))
                    response.body?.close()
                }
            })
    }

    fun addUser(data: AddUserModel, onResponse: (ResponseStatus<AddUserResponse>) -> Unit) {
        val request = NetworkClient
            .requestBuilder(usersEndpoint, NetworkClient.METHOD.POST, data.serialized())
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

                    response.body?.close()
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