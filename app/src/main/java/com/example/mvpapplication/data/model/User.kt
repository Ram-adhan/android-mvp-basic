package com.example.mvpapplication.data.model

import com.example.mvpapplication.data.network.getIntData
import com.example.mvpapplication.data.network.getStringData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
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
