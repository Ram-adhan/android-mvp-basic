package com.example.mvpapplication.data.network

import org.json.JSONObject

data class BaseResponse<T>(
    val method: String,
    val status: Boolean,
    val result: T? = null
) {
    constructor(data: JSONObject, result: T): this(
        method = data.getStringData("method", ""),
        status = data.getBooleanData("status", false),
        result = result
    )
}
