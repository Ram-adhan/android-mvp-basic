package com.example.mvpapplication

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpapplication.data.model.User
import com.example.mvpapplication.data.network.NetworkClient
import com.example.mvpapplication.data.network.ResponseStatus
import com.example.mvpapplication.data.network.api.ReqresApi
import com.example.mvpapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val networkClient: NetworkClient = NetworkClient()
    private val api: ReqresApi = ReqresApi()
    private val adapter: UserAdapter by lazy { UserAdapter() }
    private val recipeLiveData = MutableLiveData<List<User>>(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRecipes.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        recipeLiveData.observe(this) {
            adapter.submitList(it)
        }

        binding.btnAsyncCall.setOnClickListener {
            showProgress(true)
            api.getUserPagination { response ->
                CoroutineScope(Dispatchers.Main).launch {
                    when(response) {
                        is ResponseStatus.Success -> {
                            adapter.submitList(response.data)
                        }
                        is ResponseStatus.Failed -> {
                            AlertDialog.Builder(this@MainActivity)
                                .setMessage(response.message)
                                .create()
                                .show()
                        }
                    }
                    showProgress(false)
                }
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
}