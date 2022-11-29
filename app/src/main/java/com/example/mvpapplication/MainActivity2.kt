package com.example.mvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvpapplication.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val accountDetail = intent.getStringExtra("accountDetail")
        val transactionAmount = intent.getStringExtra("transactionAmount")

        binding.textView1.text = "$accountDetail\n$transactionAmount"
    }
}