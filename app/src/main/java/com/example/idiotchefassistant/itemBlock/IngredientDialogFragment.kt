package com.example.idiotchefassistant.itemBlock

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.searchEditText
        binding.listViewItems

        // Set up the list and adapter
        ingredientRepository = IngredientRepository()
        ingredientFactory = IngredientFactory(ingredientRepository)
        ingredientViewModel = ViewModelProviders.of(this, ingredientFactory).get(IngredientViewModel::class.java)

        ingredientViewModel.callBack().observe(this, Observer {
            val items = it.ingredientNames?: emptyArray()
            binding.listViewItems.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        })

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
}
