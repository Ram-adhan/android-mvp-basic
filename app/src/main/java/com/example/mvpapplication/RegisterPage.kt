package com.example.mvpapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvpapplication.databinding.ActivityRegisterPageBinding
import com.example.mvpapplication.databinding.LayoutRegisterBinding

class RegisterPage : AppCompatActivity() {
    companion object {
        var number = 1
        private const val USERNAME_DATA = "username"

        fun getIntent(context: Context): Intent {
            return Intent(context, RegisterPage::class.java).apply {

            }
        }
    }

    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("user_data", binding.edtUsername.text.toString())
            })
            finish()
        }

    }
}