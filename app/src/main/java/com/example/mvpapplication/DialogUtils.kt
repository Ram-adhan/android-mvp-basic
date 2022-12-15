package com.example.mvpapplication

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window

fun Context.createLoadingDialog(): Dialog {
    val dialogProgress = Dialog(this)
    dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialogProgress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialogProgress.setContentView(R.layout.loading_progress)
    dialogProgress.setCancelable(false)
    return dialogProgress
}