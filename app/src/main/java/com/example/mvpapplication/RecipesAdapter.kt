package com.example.mvpapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpapplication.data.model.Recipe
import com.example.mvpapplication.databinding.ItemRecipeBinding

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {
    private var data = listOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesAdapter.ViewHolder {
        return ViewHolder(ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecipesAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe) {
            binding.tvTitle.text = item.title
            binding.tvThumbnail.text = item.thumb
            binding.tvTimes.text = item.times
            binding.tvServing.text = item.serving
            binding.tvDifficulty.text = item.difficulty
        }
    }

    fun submitList(data: List<Recipe>) {
        val initSize = itemCount
        this.data = listOf()
        notifyItemRangeRemoved(0, initSize)
        this.data = data
        notifyItemRangeInserted(0, this.data.size)
    }
}