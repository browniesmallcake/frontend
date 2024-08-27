package com.example.idiotchefassistant.RecipeBlock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idiotchefassistant.databinding.RecipeItemListBinding

class RecipeItemAdapter(private val items: List<RecipeItem>) :
    RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(private val binding: RecipeItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = items[position]
                    itemClickListener?.onItemClick(item)
                }
            }
        }

        fun bind(item: RecipeItem) {
            binding.itemTitle.text = item.Title
            binding.itemDesc.text = item.description
            binding.itemReview.text = item.review.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(item: RecipeItem)
    }
}