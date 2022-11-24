package com.example.mvpapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvpapplication.databinding.LayoutRegisterBinding

class RegisterFragment: Fragment() {
    companion object {
        const val TAG = "RegisterFragment"
    }
    private lateinit var binding: LayoutRegisterBinding
    private var registerInterface: FragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            registerInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            registerInterface?.onClickConfirm()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    interface FragmentListener {
        fun onClickConfirm()
    }
}