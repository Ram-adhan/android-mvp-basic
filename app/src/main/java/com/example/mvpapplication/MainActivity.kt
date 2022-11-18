package com.example.mvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.widget.doOnTextChanged
import com.example.mvpapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val usernameText: String get() = binding.edtUsername.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate layout ke binding
        binding = ActivityMainBinding.inflate(layoutInflater)
//        usernameText = binding.edtUsername.text.toString()

        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
        }

        binding.edtUsername.doOnTextChanged { _, _, _, _ ->
            validate()
        }

        binding.edtPassword.doOnTextChanged { _, _, _, _ ->
            validate()
        }
    }

    private fun validate() {
        binding.btnLogin.isEnabled =
            binding.edtUsername.text.toString().isNotBlank()
                    && binding.edtPassword.text.toString().isNotBlank()
                    && isPasswordValid
    }

    private val isPasswordValid: Boolean get() {
        val passText = binding.edtPassword.text.toString()

        val isValid = passText.contains("[a-z]".toRegex())
                && passText.contains("[A-Z]".toRegex())
                && passText.contains("[0-9]".toRegex())
                && passText.length >= 8

        binding.tvErrorPassword.isInvisible = isValid

        return isValid
    }
}