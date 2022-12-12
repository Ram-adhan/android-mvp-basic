package com.example.mvpapplication.feature.login

class LoginPresenter {
    companion object {
        const val PASSWORD_NOT_CONTAIN_LOWERCASE = 0
        const val PASSWORD_NOT_CONTAIN_NUMBER = 2
        const val PASSWORD_ERROR = 9
        const val USERNAME_ERROR = 10
    }

    private var view: LoginView? = null

    fun onAttach(view: LoginView) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    fun validateCredential(userName: String, password: String) {
        view?.onLoading()
        val isPasswordValid = password.contains("[a-z]".toRegex())
                && password.contains("[A-Z]".toRegex())
                && password.contains("[0-9]".toRegex())
                && password.length >= 8

        val isUsernameValid = userName.length > 5
        view?.onErrorPassword(false, "")

        if (isPasswordValid && isUsernameValid) {
            view?.onSuccessLogin()
        } else if (!isUsernameValid) {
            view?.onError(USERNAME_ERROR, "invalid username")
        } else {
            if (!password.contains("[a-z]".toRegex())) {
                view?.onErrorPassword(true, "password tidak mengandung lowercase")
            } else if (!password.contains("[0-9]".toRegex())) {
                view?.onErrorPassword(false, "password tidak mengandung angka")
            } else {
                view?.onError(PASSWORD_ERROR, "invalid password")
            }
        }

        view?.onFinishedLoading()

    }
}