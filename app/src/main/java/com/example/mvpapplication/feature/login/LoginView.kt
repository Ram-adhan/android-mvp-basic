package com.example.mvpapplication.feature.login

interface LoginView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onErrorPassword(visible: Boolean, message: String)
    fun resetPasswordError()
    fun onSuccessLogin()
}