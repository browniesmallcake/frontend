package com.example.idiotchefassistant.itemBlock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IngredientViewModel(private var ingredientRepository: IngredientRepository): ViewModel() {
    private var userLiveData = MutableLiveData<IngredientData>()

    fun callBack(): LiveData<IngredientData> {
        ingredientRepository.loadIngredient(object : OnTaskFinish {
            override fun onFinish(data: IngredientData) {
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }
}