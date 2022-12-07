package com.example.mvpapplication.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvpapplication.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NameProduct") ?: "-"

        binding.tvName.text = name
    }
}