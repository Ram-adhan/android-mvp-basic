package com.example.mvpapplication.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvpapplication.databinding.ActivityMainBinding
import com.example.mvpapplication.model.dto.RestDevObject
import com.example.mvpapplication.model.network.Network
import com.example.mvpapplication.model.services.RESTfulDevService
import com.example.mvpapplication.shared.view.LoadingHandler
import com.example.mvpapplication.shared.view.LoadingHandlerImpl

class MainActivity : AppCompatActivity(), MainView, LoadingHandler by LoadingHandlerImpl() {
    private lateinit var binding: ActivityMainBinding
    private val presenter: MainPresenter by lazy {
        MainPresenter(RESTfulDevService(Network.client))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeLoadingDialog(this)
        presenter.onAttach(this)
    }

    override fun onAttached() {

    }

    override fun onLoading() {
        stackProgress()
    }

    override fun onFinishLoading() {
        stackProgress(false)
    }

    override fun onSuccessGetObjects(objects: List<RestDevObject>) {
        Log.d("MainActivity", "success get object: $objects")
    }

    override fun onFailed(message: String) {
        Log.d("MainActivity", "error: $message")
    }
}