package com.example.mvpapplication.feature.userlist

import com.example.mvpapplication.data.model.User

interface MainContract {
    interface View {
        fun onLoading()
        fun onFinishedLoading()
        fun onError(message: String)
        fun onSuccessGetUsers(users: List<User>) {}
        fun onSuccessAddUser() {}
    }
}