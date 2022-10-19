package com.example.mvpapplication.data.network.api

import com.example.mvpapplication.data.model.Recipe
import com.example.mvpapplication.data.network.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class RecipesApi {
    fun getRecipes(onFinished: (ResponseStatus<List<Recipe>>) -> Unit) {
        NetworkClient
            .client
            .newCall(
                NetworkClient.requestBuilder("/recipes")
            )
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFinished.invoke(ResponseStatus.Failed(1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val jsonObject = JSONObject(response.body!!.string())
                        onFinished.invoke(mapResultRecipes(jsonObject))
                    } else {
                        onFinished.invoke(mapFailedResponse(response))
                    }
                }
            })
    }
    private fun mapResultRecipes(jsonObject: JSONObject): ResponseStatus<List<Recipe>> {
        val resultList = jsonObject.getJSONArray("results")
        val data = mutableListOf<Recipe>()
        for (i in 0 until resultList.length()) {
            val obj = resultList.getJSONObject(i)
            data.add(
                Recipe(
                    title = obj.getStringData("title", ""),
                    thumb = obj.getStringData("thumb", ""),
                    key = obj.getStringData("key", ""),
                    times = obj.getStringData("times", ""),
                    serving = obj.getStringData("serving", ""),
                    difficulty = obj.getStringData("difficulty", "")
                )
            )
        }
        val baseResponse = BaseResponse(jsonObject, null)
        return ResponseStatus.Success(baseResponse.method, baseResponse.status, data)
    }
}