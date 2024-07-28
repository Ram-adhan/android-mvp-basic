package com.example.mvpapplication.shared.arch

interface BaseView {
    fun onAttached()
    fun onLoading()
    fun onFinishLoading()
    fun onFailed(message: String)
}
