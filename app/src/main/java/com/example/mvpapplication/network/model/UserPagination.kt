package com.example.mvpapplication.network.model

import com.example.mvpapplication.network.model.User

data class UserPagination(
    val page: Int = 0,
    val perPage: Int = 0,
    val total: Int = 0,
    val totalPage: Int = 0,
    val data: List<User> = listOf()
)
