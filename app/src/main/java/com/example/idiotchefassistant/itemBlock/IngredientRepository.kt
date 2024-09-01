package com.example.idiotchefassistant.itemBlock

import java.util.concurrent.Executors

class IngredientRepository {
    fun loadIngredient(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val ingredients = IngredientData()
            ingredients.ingredientNames = arrayOf("beef", "chicken", "pork")
            Thread.sleep(3000)
            task.onFinish(ingredients)
        }
    }
}

interface OnTaskFinish{
    fun onFinish(data: IngredientData) {
        data.ingredientNames?.forEach {
                name ->
        }
    }
}