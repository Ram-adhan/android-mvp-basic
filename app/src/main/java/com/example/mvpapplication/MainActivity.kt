package com.example.mvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.mvpapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginFragmentInterface {
    private lateinit var binding: ActivityMainBinding
    private val fragmentTransaction: FragmentTransaction by lazy {
        supportFragmentManager.beginTransaction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentTransaction
            .add(binding.fragmentContainer.id, LoginFragment(), LoginFragment.TAG)
            .commit()
    }

    override fun onClickLogin() {
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
    }

    override fun onClickForgot() {

    }
}