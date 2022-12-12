package com.example.mvpapplication.feature.login

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.mvpapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var binding: ActivityLoginBinding
    private val presenter = LoginPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onAttach(this)

        binding.btnLogin.setOnClickListener {
            presenter.validateCredential(
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        binding.edtUsername.doOnTextChanged { text, start, before, count ->
            validateInput()
        }

        binding.edtPassword.doOnTextChanged { text, start, before, count ->
            validateInput()
        }
    }

    private fun validateInput() {
        binding.btnLogin.isEnabled =
            binding.edtPassword.text.toString().isNotBlank() && binding.edtUsername.text.toString()
                .isNotBlank()
    }

    override fun onLoading() {
        binding.progressIndicator.isVisible = true
    }

    override fun onFinishedLoading() {
        binding.progressIndicator.isVisible = false
    }

    override fun onError(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Ok", this::dialogClickListener)
            .setNegativeButton("Cancel", this::dialogClickListener)
            .create()
            .show()
    }

    private fun dialogClickListener(dialogInterface: DialogInterface, button: Int) {
        when (button) {
            DialogInterface.BUTTON_NEGATIVE -> {}
            DialogInterface.BUTTON_POSITIVE -> {}
            DialogInterface.BUTTON_NEUTRAL -> {}
        }
    }

    override fun onSuccessLogin() {
        Toast.makeText(this, "Success Login", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}