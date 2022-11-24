package com.example.mvpapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvpapplication.databinding.LayoutLoginBinding

class LoginFragment: Fragment() {
    companion object {
        const val TAG = "LoginFragment"
    }

    private lateinit var binding: LayoutLoginBinding
    private var loginInterface: LoginFragmentInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginFragmentInterface) {
            loginInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            loginInterface?.onClickLogin()
        }

        binding.tvForgotPass.setOnClickListener {
            loginInterface?.onClickForgot()
        }
    }
}