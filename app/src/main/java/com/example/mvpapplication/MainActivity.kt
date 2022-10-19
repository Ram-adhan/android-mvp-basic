package com.example.mvpapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpapplication.data.model.Recipe
import com.example.mvpapplication.data.network.NetworkClient
import com.example.mvpapplication.data.network.ResponseStatus
import com.example.mvpapplication.data.network.api.RecipesApi
import com.example.mvpapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val networkClient: NetworkClient = NetworkClient()
    private val recipesApi: RecipesApi = RecipesApi()
    private val adapter: RecipesAdapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRecipes.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.btnAsyncCall.setOnClickListener {
            showProgress(true)
            recipesApi.getRecipes {
                when(it) {
                    is ResponseStatus.Failed -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    is ResponseStatus.Success -> {
                        submitRecipesData(it.data)
                    }
                }
                showProgress(false)
            }
        }

        binding.btnSyncCall.setOnClickListener {
            showProgress(true)
            CoroutineScope(Dispatchers.IO).launch {
                networkClient.getSync("/recipes").use {
                    showProgress(false)
                }
            }
        }
    }

    private fun showProgress(isShown: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressIndicator.isVisible = isShown
        }
    }

    private fun submitRecipesData(data: List<Recipe>) {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.submitList(data)
        }
    }
}