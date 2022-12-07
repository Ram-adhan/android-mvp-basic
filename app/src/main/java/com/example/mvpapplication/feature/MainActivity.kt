package com.example.mvpapplication.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpapplication.data.model.MakeUpItem
import com.example.mvpapplication.data.network.MakeupApi
import com.example.mvpapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressIndicator.isVisible = true
        adapter.setListener(this::onAdapterClick)
        binding.rvRecipes.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        MakeupApi().getProducts { data ->
            CoroutineScope(Dispatchers.Main).launch {
                binding.progressIndicator.isVisible = false
                data?.let { list ->
                    adapter.submitList(list.also {
                        it[0].priceSign = "Rp"
                    })
                }
            }
            Log.d("MainActivity", "result -> ${data?.count()}")
        }
    }

    private val adapterListener: (MakeUpItem) -> Unit = { item ->
        startActivity(
            Intent(this, MainActivity2::class.java).apply {
                putExtra("NameProduct", item.name)
            }
        )
    }

    private fun onAdapterClick(item: MakeUpItem) {
        startActivity(
            Intent(this, MainActivity2::class.java).apply {
                putExtra("NameProduct", item.name)
            }
        )
    }
}