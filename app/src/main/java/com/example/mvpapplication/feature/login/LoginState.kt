package com.example.mvpapplication.feature.login

sealed class LoginState {
    object Loading: LoginState()
    data class OnError(val throwable: Throwable): LoginState()
    data class OnSuccessLogin(val message: String): LoginState()
}
