package com.example.mvpapplication.feature.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvpapplication.data.model.User
import com.example.mvpapplication.databinding.ItemRecipeBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val data = mutableListOf<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun submitList(list: List<User>) {
        val initSize = itemCount
        data.clear()
        notifyItemRangeRemoved(0, initSize)
        data.addAll(list)
        notifyItemRangeInserted(0, data.size)
    }

    inner class ViewHolder(private val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: User) {
            with(binding) {
                tvName.text = "${item.firstName} ${item.lastName}"
                tvThumbnail.text = item.avatar
                tvEmail.text = item.email
                Glide
                    .with(root.context)
                    .load(item.avatar)
                    .into(ivAvatar)
            }
        }
    }
}