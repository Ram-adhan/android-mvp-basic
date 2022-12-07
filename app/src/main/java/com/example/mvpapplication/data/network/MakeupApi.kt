package com.example.mvpapplication.data.network

import com.example.mvpapplication.data.model.MakeUpItem
import com.example.mvpapplication.data.model.deserializeJson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class MakeupApi {
    fun getProducts(onResponse: (List<MakeUpItem>?) -> Unit) {
        val request = Request.Builder()
            .url("${HttpClient.baseUrl}${HttpClient.productEndpoint}")
            .build()

        HttpClient.client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val result = mutableListOf<MakeUpItem>()
                        val jsonArray = JSONArray(response.body?.string())
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = deserializeJson<MakeUpItem>(jsonArray.getString(i))
                            jsonObject?.let { result.add(it) }
                        }

                        onResponse.invoke(result)
                    }
                    response.body?.close()
                }
            })
    }
}