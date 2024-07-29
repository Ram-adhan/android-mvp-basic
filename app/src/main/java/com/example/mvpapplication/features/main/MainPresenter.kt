package com.example.mvpapplication.features.main

import com.example.mvpapplication.model.dto.Detail
import com.example.mvpapplication.model.dto.RestDevObjectRequest
import com.example.mvpapplication.model.services.RESTfulDevService
import com.example.mvpapplication.shared.arch.BasePresenter
import kotlinx.coroutines.launch

class MainPresenter(private val restfulDevService: RESTfulDevService) : BasePresenter<MainView>() {

  override fun onAttach(view: MainView) {
    super.onAttach(view)
    getAllObjects()
  }

  fun getAllObjects() {
    launch {
      view?.onLoading()
      val result = restfulDevService.getAllObjects()
      result.fold(
        onSuccess = { view?.onSuccessGetObjects(it) },
        onFailure = { view?.onFailed(it.message.toString()) },
      )
      view?.onFinishLoading()
    }
  }

  fun onClickPost() {
    view?.onLoading()
    launch {
      val item = RestDevObjectRequest(name = "Nokia NGage", data = Detail(capacity = "256 MB"))
      val result = restfulDevService.addObject(item)
      result.fold(
        onSuccess = { view?.onSuccessAddObject(it) },
        onFailure = { view?.onFailed(it.message.toString()) },
      )
      view?.onFinishLoading()
    }
  }
}
