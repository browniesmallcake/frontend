package com.example.idiotchefassistant.ItemBlock

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idiotchefassistant.databinding.ActivityResultPageBinding

class ResultPage : AppCompatActivity(), IngredientItemAdapter.OnItemClickListener {
    private lateinit var binding: ActivityResultPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResultPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            IngredientDialogFragment().show(supportFragmentManager, "customDialog")
        }

        val items = generateFakeData(10)
        val recycleView = binding.recyclerViewIngredients
        recycleView.layoutManager = LinearLayoutManager(this)

        val adapter = IngredientItemAdapter(items)
        adapter.setOnItemClickListener(this)
        recycleView.adapter = adapter

        binding.searchButton
    }

    override fun onItemClick(item: IngredientItem) {
        IngredientDialogFragment().show(supportFragmentManager, "customDialog")
    }

    private fun generateFakeData(cnt: Int): List<IngredientItem> {
        return List(cnt) { IngredientItem("Item ${it + 1}") }
    }
}

