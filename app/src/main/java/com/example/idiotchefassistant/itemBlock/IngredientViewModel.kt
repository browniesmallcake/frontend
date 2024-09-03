package com.example.idiotchefassistant.itemBlock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IngredientViewModel(private var ingredientRepository: IngredientRepository): ViewModel() {
    private var userLiveData = MutableLiveData<IngredientData>()

    fun callBack(): LiveData<IngredientData> {
        ingredientRepository.loadData(object : OnTaskFinish {
            override fun onFinish(data: IngredientData) {
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }

    fun filterItems(query: String) {
        val allItems = ingredientRepository.getDatas()
        val data = IngredientData()
        data.ingredientNames = allItems.filter { it.contains(query, ignoreCase = true) }.toTypedArray()
        userLiveData.postValue(data)
    }
}