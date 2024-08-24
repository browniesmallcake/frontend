package com.example.idiotchefassistant.ItemBlock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idiotchefassistant.databinding.RecyclerViewItemBinding;

class IngredientItemAdapter(private val items: kotlin.collections.List<IngredientItem>) :
    RecyclerView.Adapter<IngredientItemAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(private val binding:RecyclerViewItemBinding) :
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

        fun bind(item: IngredientItem) {
            binding.itemTitle.text = item.Title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(item: IngredientItem)
    }
}