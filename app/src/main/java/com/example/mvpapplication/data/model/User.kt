package com.example.mvpapplication.data.model

import com.example.mvpapplication.data.network.getIntData
import com.example.mvpapplication.data.network.getStringData
import org.json.JSONObject

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
) {
    constructor(data: JSONObject): this(
        id = data.getIntData("id"),
        email = data.getStringData("email", "-"),
        firstName = data.getStringData("first_name", "-"),
        lastName = data.getStringData("last_name", "-"),
        avatar = data.getStringData("avatar", "-")
    )
}
