package com.example.idiotchefassistant.itemBlock

import java.util.concurrent.Executors

class IngredientRepository {
    fun loadIngredient(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val ingredients = IngredientData()
            ingredients.ingredientNames = arrayOf("beef", "chicken", "pork", "tomato", "banana", "potato", "egg")
            Thread.sleep(500)
            task.onFinish(ingredients)
        }
    }
}

interface OnTaskFinish{
    fun onFinish(data: IngredientData)
}