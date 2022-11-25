package com.example.mvpapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpapplication.databinding.ItemNotifBinding

class AdapterNotif(private val data: MutableList<String> = mutableListOf()): RecyclerView.Adapter<AdapterNotif.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemNotifBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: String) {
            binding.tvTitle.text = item
        }
    }
}