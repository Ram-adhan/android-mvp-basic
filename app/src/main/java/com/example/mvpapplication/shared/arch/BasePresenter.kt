package com.example.mvpapplication.shared.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V: BaseView>: CoroutineScope {
    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = supervisorJob + Dispatchers.Main

    protected var view: V? = null

    open fun onAttach(view: V) {
        this.view = view
    }

}
