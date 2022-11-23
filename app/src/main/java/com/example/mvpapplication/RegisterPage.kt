package com.example.mvpapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvpapplication.databinding.LayoutRegisterBinding

class RegisterPage : AppCompatActivity() {
    companion object {
        var number = 1
        private const val USERNAME_DATA = "username"

        fun getIntent(context: Context, userName: String): Intent {
            return Intent(context, RegisterPage::class.java).apply {
                putExtra(USERNAME_DATA, userName)
            }
        }
    }

    private lateinit var binding: LayoutRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list = listOf(1, 2, 3)
        number = 2
        binding.tvText.text = list?.joinToString { "" }

        Log.d("RegisterPage", "intent data: ${intent.getStringExtra(USERNAME_DATA)}")

    }
}