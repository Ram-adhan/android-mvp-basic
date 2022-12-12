package com.example.mvpapplication.feature.login

class LoginBiometricView: LoginView {
    private val presenter = LoginPresenter()

    fun onCreate() {
        presenter.onAttach(this)
    }

    override fun onLoading() {
        TODO("Not yet implemented")
    }

    override fun onFinishedLoading() {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccessLogin() {
        TODO("Not yet implemented")
    }
}