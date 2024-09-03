package com.example.idiotchefassistant.itemBlock

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.idiotchefassistant.databinding.FragmentIngredientDialogBinding

class IngredientDialogFragment : DialogFragment() {
    private var _binding: FragmentIngredientDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var ingredientViewModel: IngredientViewModel
    private lateinit var ingredientFactory: IngredientFactory
    private lateinit var ingredientRepository: IngredientRepository
    private var selectedPosition: Int? = null
    private var selectedItemName: String? = null
    private var listener: OnItemSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.listViewItems
        ingredientRepository = IngredientRepository()
        ingredientFactory = IngredientFactory(ingredientRepository)
        ingredientViewModel = ViewModelProviders.of(this, ingredientFactory).get(IngredientViewModel::class.java)
        // Set up the list and adapter
        val adapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (position == selectedPosition) {
                    view.setBackgroundColor(resources.getColor(android.R.color.holo_orange_light))
                } else {
                    view.setBackgroundColor(resources.getColor(android.R.color.transparent))
                }
                return view
            }
        }
        binding.listViewItems.adapter = adapter
        ingredientViewModel.callBack().observe(this, Observer {
            val items = it.ingredientNames?: emptyArray()
            adapter.clear()
            adapter.addAll(items.toList())
            adapter.notifyDataSetChanged()
        })
        binding.listViewItems.setOnItemClickListener { _, view, position, _ ->
            // 記錄選中的位置和名稱
            selectedPosition = position
            selectedItemName = adapter.getItem(position)
            // 刷新列表來更新選中效果
            adapter.notifyDataSetChanged()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                ingredientViewModel.filterItems(query)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.buttonCancel.setOnClickListener { dismiss() }
        binding.buttonConfirm.setOnClickListener {
            listener?.onItemSelected(selectedItemName)
            dismiss()
        }

        binding.buttonCancel.setOnClickListener { dismiss() }
        binding.buttonConfirm.setOnClickListener {
            // Handle confirmation logic
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        this.listener = listener
    }
}

interface OnItemSelectedListener {
    fun onItemSelected(itemName: String?)
}
