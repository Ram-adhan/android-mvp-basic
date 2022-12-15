package com.example.mvpapplication.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvpapplication.data.network.ResponseStatus
import com.example.mvpapplication.data.network.api.CredentialApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val credentialApi: CredentialApi,
): ViewModel() {
    private var _state: MutableLiveData<LoginState> = MutableLiveData()
    val state: LiveData<LoginState> = _state

    fun login(email: String, password: String) {
        if (validateCredential(email, password)) {
            _state.value = LoginState.Loading
            viewModelScope.launch {
                credentialApi
                    .registerUser(email, password)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        when (it) {
                            is ResponseStatus.Success -> _state.postValue(LoginState.OnSuccessLogin("Success"))
                            is ResponseStatus.Failed -> _state.postValue(LoginState.OnError(
                                Throwable(it.message)
                            ))
                        }
                    }
            }
        }

    }

    private fun validateCredential(userName: String, password: String): Boolean {
        val isPasswordValid = password.contains("[a-z]".toRegex())
                && password.contains("[A-Z]".toRegex())
                && password.contains("[0-9]".toRegex())
                && password.length >= 8

        val isUsernameValid = userName.length > 5

        if (!isPasswordValid) {
            _state.value = LoginState.OnError(Throwable("invalid Password"))
        }
        if (!isUsernameValid) {
            _state.value = LoginState.OnError(Throwable("invalid Username"))
        }

        return isPasswordValid && isUsernameValid
    }

}