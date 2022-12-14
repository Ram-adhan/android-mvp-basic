package com.example.mvpapplication.feature.login

import com.example.mvpapplication.data.model.User
import com.example.mvpapplication.data.model.UserPagination

interface LoginView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onErrorPassword(visible: Boolean, message: String)
    fun resetPasswordError()
    fun onSuccessLogin(username: String, password: String)
    fun onSuccessGetUser(user: UserPagination)
    fun onSuccessRegister()
}