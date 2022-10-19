package com.example.mvpapplication.data.network

import okhttp3.Response
import org.json.JSONObject

fun mapFailedResponse(response: Response): ResponseStatus.Failed {
    val jsonBody = response.body?.string().let { if(!it.isNullOrEmpty()) JSONObject(it) else JSONObject()}
    return ResponseStatus.Failed(response.code, jsonBody.getStringData("message", ""))
}

fun JSONObject.getStringData(key: String, default: String): String {
    return if (this.has(key)) {
        this.getString(key)
    } else {
        default
    }
}

fun JSONObject.getBooleanData(key: String, default: Boolean): Boolean {
    return if (this.has(key)) {
        this.getBoolean(key)
    } else {
        default
    }
}