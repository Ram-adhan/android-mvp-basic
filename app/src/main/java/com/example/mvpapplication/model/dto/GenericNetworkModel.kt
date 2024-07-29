package com.example.mvpapplication.model.dto

data class ResponseFailure(val code: String, override val message: String) : Throwable()
