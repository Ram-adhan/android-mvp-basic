package com.example.mvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterNotif: AdapterNotif

    private val listTransactionTitle = listOf(
        "Transaksi Masuk",
        "Transaksi Keluar",
        "Transaksi Masuk",
        "Transaksi Keluar",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterNotif = AdapterNotif(listTransactionTitle.toMutableList())

        val layoutManager = LinearLayoutManager(this)
        binding.rvTransaction.adapter = adapterNotif
        binding.rvTransaction.layoutManager = layoutManager

    }
}