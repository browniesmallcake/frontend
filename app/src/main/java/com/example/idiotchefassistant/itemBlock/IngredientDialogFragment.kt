package com.example.idiotchefassistant.itemBlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.idiotchefassistant.databinding.FragmentIngredientDialogBinding

class IngredientDialogFragment : DialogFragment() {
    private var _binding: FragmentIngredientDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.searchEditText
        binding.listViewItems
        binding.buttonCancel.setOnClickListener { dismiss() }
        binding.buttonConfirm.setOnClickListener {
            // Handle confirmation logic
            dismiss()
        }

        // Set up the list and adapter
        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        binding.listViewItems.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
