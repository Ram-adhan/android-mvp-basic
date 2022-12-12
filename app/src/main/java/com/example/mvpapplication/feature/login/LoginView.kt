package com.example.mvpapplication.feature.login

interface LoginView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(message: String)
    fun onSuccessLogin()
}