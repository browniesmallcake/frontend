package com.example.idiotchefassistant

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.idiotchefassistant.databinding.ActivityRecipePageBinding

class RecipePage : AppCompatActivity() {
    private lateinit var binding: ActivityRecipePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_recipe_page)
        binding = ActivityRecipePageBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}