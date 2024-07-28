package com.example.mvpapplication.features.main

import com.example.mvpapplication.model.dto.Detail
import com.example.mvpapplication.model.dto.NetworkResult
import com.example.mvpapplication.model.dto.RestDevObject
import com.example.mvpapplication.model.dto.RestDevObjectRequest
import com.example.mvpapplication.model.services.RESTfulDevService
import com.example.mvpapplication.shared.arch.BasePresenter
import kotlinx.coroutines.launch

class MainPresenter(private val restfulDevService: RESTfulDevService): BasePresenter<MainView>() {

    override fun onAttach(view: MainView) {
        super.onAttach(view)
        getAllObjects()
    }

    fun getAllObjects() {
        launch {
            view?.onLoading()
            when (val result = restfulDevService.getAllObjects()) {
                is NetworkResult.Success -> {
                    view?.onSuccessGetObjects(result.data)
                }
                is NetworkResult.Error -> {
                    view?.onFailed(result.message)
                }
            }
            view?.onFinishLoading()
        }
    }

    fun onClickPost() {
        view?.onLoading()
        launch {
            val item = RestDevObjectRequest(
                name = "Nokia NGage",
                data = Detail(capacity = "256 MB")
            )
            when (val result = restfulDevService.addObject(item)) {
                is NetworkResult.Success -> {
                    view?.onSuccessAddObject(result.data)
                }
                is NetworkResult.Error -> {
                    view?.onFailed(result.message)
                }
            }
            view?.onFinishLoading()
        }
    }
}
