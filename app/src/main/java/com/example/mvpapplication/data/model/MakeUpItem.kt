package com.example.mvpapplication.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MakeUpItem(
    val brand: String?,
    val name: String?,
    val price: String?,
    @Json(name = "price_sign")
    var priceSign: String?,
    @Json(name = "image_link")
    val imageLink: String?,
)
