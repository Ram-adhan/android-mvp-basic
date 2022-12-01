package com.example.mvpapplication.network.api

import com.example.mvpapplication.network.Mapper.toUserPagination
import com.example.mvpapplication.network.model.UserPagination
import com.example.mvpapplication.network.NetClient

class UserApi {
    fun getUsers(page: Int = 1, onResponse: (UserPagination?, Throwable?) -> Unit) {
        NetClient
            .getApi("/users?page$page", null) { responseBody, error ->
                if (error == null) {
                    val userPagination: UserPagination = responseBody?.toUserPagination() ?: UserPagination()
                    onResponse.invoke(userPagination, null)
                } else {
                    onResponse.invoke(null, error)
                }
            }
    }
}