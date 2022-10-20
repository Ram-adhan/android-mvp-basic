package com.example.mvpapplication.data.network

sealed class ResponseStatus<out T> {
    data class Failed(val code: Int, val message: String, val throwable: Throwable? = null): ResponseStatus<Nothing>()
    data class Success<out T>(val data: T, val method: String = "", val status: Boolean = true): ResponseStatus<T>()
    data class SuccessPagination<out T>(
        val page: Int,
        val perPage: Int,
        val total: Int,
        val totalPages: Int,
        val data: T
    ): ResponseStatus<T>()
}
