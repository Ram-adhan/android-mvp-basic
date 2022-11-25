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
        ItemNotifModel("Transaksi Masuk", "14:30", "Ahmad Alfiansyah - 4434 5384 3478", "Rp. 150.000"),
        ItemNotifModel("Transaksi Keluar", "14:35", "Ahmad A - 4434 5384 3478", "Rp. 20.000"),
        ItemNotifModel("Transaksi Masuk", "14:40", "Ahmad Alfi - 4434 5384 3478", "Rp. 1.500.000"),
        ItemNotifModel("Transaksi Keluar", "15:30", "Rizky Alfiansyah - 4434 5384 3478", "Rp. 150.000"),
        ItemNotifModel("Transaksi Keluar", "15:30", "Rizky Alfiansyah - 4434 5384 3478", "Rp. 150.000"),
        ItemNotifModel("Transaksi Keluar", "15:30", "Rizky Alfiansyah - 4434 5384 3478", "Rp. 150.000"),
        ItemNotifModel("Transaksi Keluar", "15:30", "Rizky Alfiansyah - 4434 5384 3478", "Rp. 150.000"),
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