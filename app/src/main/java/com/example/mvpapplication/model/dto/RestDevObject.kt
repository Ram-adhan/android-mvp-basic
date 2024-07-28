package com.example.mvpapplication.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RestDevObject(
    val id: String,
    val name: String,
)
