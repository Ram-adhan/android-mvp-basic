package com.example.mvpapplication.feature.userlist

import com.example.mvpapplication.data.model.AddUserModel
import com.example.mvpapplication.data.network.ResponseStatus
import com.example.mvpapplication.data.network.api.UserApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainPresenter(
    private val view: MainContract.View,
    private val api: UserApi,
    uiContext: CoroutineContext = Dispatchers.Main
) {
    private val supervisorJob: Job = SupervisorJob()
    private val scope = CoroutineScope(supervisorJob + uiContext)

    fun onAttach(view: MainContract.View) {
        getUsers()
        api.getError {
            scope.launch {
                when (it) {
                    is ResponseStatus.Failed -> view.onError(it.message)
                    else -> {}
                }
            }
        }
    }

    fun onDetach() {
        scope.cancel()
    }

    fun getUsers(page: Int = 1) {
        view.onLoading()
        api.getUserPagination {
            scope.launch {
                when (it) {
                    is ResponseStatus.Success -> view.onSuccessGetUsers(it.data)
                    is ResponseStatus.Failed -> view.onError(it.message)
                }
                view.onFinishedLoading()
            }
        }
    }

    fun addUsers(name: String, job: String) {
        view.onLoading()
        api.addUser(AddUserModel(name, job)) {
            scope.launch {
                when (it) {
                    is ResponseStatus.Success -> view.onSuccessAddUser()
                    is ResponseStatus.Failed -> view.onError(it.message)
                }
                view.onFinishedLoading()
            }
        }
    }
}