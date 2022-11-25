package com.example.mvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.mvpapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginFragmentInterface{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, LoginFragment(), LoginFragment.TAG)
            .commit()
    }

    override fun onClickLogin() {
        Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
        getLoginFragment()?.typeToUsername("Budi")
    }

    override fun onClickForgot() {
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, RegisterFragment(), RegisterFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    private fun getLoginFragment(): LoginFragment? {
        val fragment = supportFragmentManager.findFragmentByTag(LoginFragment.TAG)
        return if (fragment != null) {
            fragment as LoginFragment
        } else {
            null
        }
    }
}