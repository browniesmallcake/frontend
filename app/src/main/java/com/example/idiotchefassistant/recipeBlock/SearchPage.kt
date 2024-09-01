package com.example.idiotchefassistant.recipeBlock

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idiotchefassistant.databinding.ActivitySearchPageBinding

class SearchPage : AppCompatActivity(), RecipeItemAdapter.OnItemClickListener {
    private lateinit var binding: ActivitySearchPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_search_page)
        binding = ActivitySearchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.EditText
        val item = mutableListOf<RecipeItem>()
        item.add(RecipeItem("搜尋食譜結果", "食譜的描述", 5))
        val recycleView = binding.RecipeRecycleView
        recycleView.layoutManager = LinearLayoutManager(this)

        val adapter = RecipeItemAdapter(item)
        adapter.setOnItemClickListener(this)
        recycleView.adapter = adapter
    }

    override fun onItemClick(item: RecipeItem) {
        val intent = Intent(this, RecipePage::class.java)
        startActivity(intent)
    }
}



