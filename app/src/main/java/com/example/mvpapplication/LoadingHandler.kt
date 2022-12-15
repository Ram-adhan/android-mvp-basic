package com.example.mvpapplication

import android.app.Dialog
import android.content.Context

class LoadingHandler {
    private var loadingCount = 0
    private var _progressDialog: Dialog? = null

    fun setup(progressDialog: Dialog) {
        _progressDialog = progressDialog
    }

    fun destroy() {
        _progressDialog?.dismiss()
        _progressDialog = null
    }

    fun restart() {
        loadingCount = 0
        _progressDialog?.dismiss()
    }

    fun addLoading() {
        loadingCount += 1
        _progressDialog?.show()
    }

    fun finishLoading() {
        loadingCount -= 1
        if (loadingCount < 1) {
            _progressDialog?.dismiss()
            loadingCount = 0
        }
    }
}