package com.example.mvpapplication.features.main

import com.example.mvpapplication.model.dto.RestDevObject
import com.example.mvpapplication.shared.arch.BaseView

interface MainView: BaseView {
    fun onSuccessGetObjects(objects: List<RestDevObject>)
}
