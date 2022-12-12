package com.example.mvpapplication.feature.userlist

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpapplication.data.model.User
import com.example.mvpapplication.data.network.api.ReqresApi
import com.example.mvpapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private val adapter: UserAdapter by lazy { UserAdapter() }
    private val recipeLiveData = MutableLiveData<List<User>>(listOf())
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, ReqresApi()).apply {
            onAttach(this@MainActivity)
        }

        binding.rvRecipes.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        recipeLiveData.observe(this) {
            adapter.submitList(it)
        }

        binding.btnAsyncCall.setOnClickListener {
            presenter.getUsers()
        }

        binding.btnSyncCall.setOnClickListener {
            presenter.addUsers("Adam", "lead")
        }
    }

    override fun onLoading() {
        showProgress(true)
    }

    override fun onFinishedLoading() {
        showProgress(false)
    }

    override fun onError(message: String) {
        val dialog = AlertDialog.Builder(this)
        dialog
            .setMessage(message)
            .setPositiveButton("Ok") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    override fun onSuccessGetUsers(users: List<User>) {
        adapter.submitList(users)
    }

    private fun showProgress(isShown: Boolean) {
        binding.progressIndicator.isVisible = isShown
    }
}