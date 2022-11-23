package com.example.mvpapplication

import android.content.Context
import android.content.Intent

fun startRegisterActivity(context: Context) {
    context.startActivity(Intent(context, RegisterPage::class.java))
}