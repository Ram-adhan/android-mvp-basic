package com.example.mvpapplication

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mvpapplication.databinding.ActivityMainBinding
import com.example.mvpapplication.network.api.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAsyncCall.setOnClickListener {
            UserApi().getUsers { userPagination, error ->
                CoroutineScope(Dispatchers.Main).launch {
                    if (error == null) {
                        binding.tvResponse.text = userPagination?.data?.joinToString("\n") { it.email }
                    } else {
                        AlertDialog
                            .Builder(this@MainActivity)
                            .setMessage(error.message)
                            .create().show()
                    }
                }
            }
        }
    }
}