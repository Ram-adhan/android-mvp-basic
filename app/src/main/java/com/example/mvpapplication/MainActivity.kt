package com.example.mvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvpapplication.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val baseUrl = "https://reqres.in/api"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$baseUrl/users?page=1")
            .build()

        binding.btnAsyncCall.setOnClickListener {
            client
                .newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("OkhttpClient", "onError: ${e.message}")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d("OkhttpClient", "onResponse: ${response.body?.string()}")
                        response.body?.close()
                    }
                })
        }
    }
}