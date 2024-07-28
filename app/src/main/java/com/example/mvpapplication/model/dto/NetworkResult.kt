package com.example.mvpapplication.model.dto

sealed interface NetworkResult<out T> {
    data class Success<T : Any>(val data: T) : NetworkResult<T>

    data class Error(val code: String, val message: String) : NetworkResult<Nothing>
}
