package com.example.idiotchefassistant.resultBlock

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.idiotchefassistant.R
import com.example.idiotchefassistant.databinding.RecyclerViewItemBinding;

class ResultItemAdapter(private var items: kotlin.collections.List<ResultItem>) :
    RecyclerView.Adapter<ResultItemAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(private val binding:RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val editButton: ImageView = itemView.findViewById(R.id.edit_button)
        private val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)

//        init {
//            binding.root.setOnClickListener{
//                val position = adapterPosition
//                if(position != RecyclerView.NO_POSITION){
//                    val item = items[position]
//                    itemClickListener?.onItemClick(item)
//                }
//            }
//        }

        fun bind(item: ResultItem) {
            binding.itemTitle.text = item.Title
            editButton.setOnClickListener {
                itemClickListener?.onEditClick(item)
            }
            deleteButton.setOnClickListener {
                itemClickListener?.onDeleteClick(item)
            }
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
        this.itemClickListener = listener
    }

    fun updateItems(newItems: List<ResultItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onEditClick(item: ResultItem)
        fun onDeleteClick(item: ResultItem)
    }
}