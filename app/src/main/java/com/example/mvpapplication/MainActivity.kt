package com.example.mvpapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.widget.doOnTextChanged
import com.example.mvpapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate layout ke binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val page = RegisterPage()

        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
//            startActivity(Intent(this, RegisterPage::class.java).putExtra(
//                "key",
//                intArrayOf(1, 2, 3)
//            ))
        }

        binding.edtUsername.doOnTextChanged { _, _, _, _ ->
            validate()
        }

        binding.edtPassword.doOnTextChanged { _, _, _, _ ->
            validate()
        }

        binding.tvForgotPass.setOnClickListener {
            startRegisterActivity(this)
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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "activity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "activity onResume")
    }

    override fun onPause() {
        super.onPause()
        binding.edtPassword.text.clear()
        binding.edtUsername.text.clear()
        Log.d(TAG, "activity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "activity onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "activity onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "activity onDestroy")
    }
}