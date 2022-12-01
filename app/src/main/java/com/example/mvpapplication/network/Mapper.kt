package com.example.mvpapplication.network

import com.example.mvpapplication.network.model.User
import com.example.mvpapplication.network.model.UserPagination
import org.json.JSONObject

object Mapper {
    fun JSONObject.toUserPagination(): UserPagination {
        val userData = this.getJSONArray("data")
        val users = mutableListOf<User>()
        for (i in 0 until userData.length()) {
            val json = userData.getJSONObject(i)
            users.add(json.toUser())
        }
        return UserPagination(
            page = this.getInt("page"),
            perPage = this.getInt("per_page"),
            total = this.getInt("total"),
            totalPage = this.getInt("total_pages"),
            data = users
        )
    }

    fun JSONObject.toUser(): User {
        return User(
            id = getInt("id"),
            email = getString("email"),
            firstName = getString("first_name"),
            lastName = getString("last_name"),
            avatar = getString("avatar")
        )
    }
}