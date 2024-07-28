package com.example.mvpapplication.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RestDevObject(val id: String, val name: String, val createdAt: String? = null)

@Serializable data class RestDevObjectRequest<T : Any>(val name: String, val data: T)

@Serializable
data class Detail(
    val capacity: String,
)
