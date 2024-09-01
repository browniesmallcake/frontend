package com.example.idiotchefassistant.itemBlock

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IngredientFactory(val ingredientRepository: IngredientRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientViewModel::class.java)) {
            return IngredientViewModel(ingredientRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}