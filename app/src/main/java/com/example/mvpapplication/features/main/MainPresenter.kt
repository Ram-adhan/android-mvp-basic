package com.example.mvpapplication.features.main

import com.example.mvpapplication.model.services.RESTfulDevService
import com.example.mvpapplication.shared.arch.BasePresenter
import kotlinx.coroutines.launch

class MainPresenter(private val restfulDevService: RESTfulDevService): BasePresenter<MainView>() {

    override fun onAttach(view: MainView) {
        super.onAttach(view)
        launch {
            view.onLoading()
            val result = restfulDevService.getAllObjects()
            if (result.isNotEmpty()) {
                view.onSuccessGetObjects(result)
            } else {
                view.onFailed("Objects is empty")
            }
            view.onFinishLoading()
        }
    }
}
