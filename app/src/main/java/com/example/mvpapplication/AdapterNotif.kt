package com.example.mvpapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpapplication.databinding.ItemNotifBinding

class AdapterNotif(private val data: MutableList<ItemNotifModel> = mutableListOf()): RecyclerView.Adapter<AdapterNotif.ViewHolder>() {

    private var cardClickListener: ((ItemNotifModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], cardClickListener)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemNotifBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ItemNotifModel, listener: ((ItemNotifModel) -> Unit)?) {
            binding.tvTitle.text = item.title
            binding.tvTime.text = item.time
            binding.tvAccountInfo.text = item.accountDetail
            binding.tvTransactionAmount.text = item.transactionAmount
            binding.root.setOnClickListener {
                listener?.invoke(item)
            }
        }
    }

    fun setOnCardClickListener(listener: (ItemNotifModel) -> Unit) {
        this.cardClickListener = listener
    }
}