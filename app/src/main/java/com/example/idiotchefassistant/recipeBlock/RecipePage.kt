package com.example.idiotchefassistant.recipeBlock

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.idiotchefassistant.R
import com.example.idiotchefassistant.databinding.ActivityRecipePageBinding

class RecipePage : AppCompatActivity() {
    private lateinit var binding: ActivityRecipePageBinding
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var recipeFactory: RecipeFactory
    private lateinit var recipeRepository: RecipeRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recipeRepository = RecipeRepository()
        recipeFactory = RecipeFactory(recipeRepository)
        recipeViewModel = ViewModelProvider(this, recipeFactory)[RecipeViewModel::class.java]

        // get recipe content
        val rid = intent.getIntExtra("rid", -1)
        recipeViewModel.getData(rid)

        recipeViewModel.callBack().observe(this) { data ->
            Log.d("SearchPage", "Received items: $data")
            binding.recipeName.text = data?.title ?: "Unknown"
            val imageLink = recipeViewModel.link2Image(data?.video)
            binding.recipeImage.load(imageLink)
            imageLink.let {
                binding.recipeImage.load(it) {
                    placeholder(R.drawable.downloading_24)  // 可選的佔位符
                    error(R.drawable.broken_image_24)       // 可選的錯誤圖片
                }
            }
            val floatValue: Float = (data?.score ?: 0).toFloat()
            binding.recipeReviewNum.text = floatValue.toString()
            binding.recipeReviewStar.rating = floatValue
            // create ingredients adapter
            recipeViewModel.getIngredients(data.iids).observe(this) { ingredients ->
                val adapter = RecipeIngredientsAdapter(ingredients)
                binding.ingredients.layoutManager = LinearLayoutManager(this)
                binding.ingredients.adapter = adapter
            }
        }
    }
}

