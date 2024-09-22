package com.example.idiotchefassistant.itemBlock

import android.util.Log
import java.util.concurrent.Executors

class IngredientRepository {
    private var nowData = emptyArray<String>()
    private val listeners = mutableListOf<OnTaskFinish>()

    fun loadData(task: OnTaskFinish) {
        listeners.add(task)
        Executors.newSingleThreadExecutor().submit {
            val ingredients = IngredientData()
            ingredients.ingredientNames = nowData
            task.onFinish(ingredients)
            Log.i("ingredients name in repository: ", nowData.joinToString(", "))
        }
    }
    fun getData(): Array<String> {
        return nowData
    }
    fun setData(newData: Array<String>) {
        nowData = newData
        notifyListeners()
//        Log.i("ingredients name in repository: ", nowData.joinToString(", "))
    }
    private fun notifyListeners() {
        val ingredients = IngredientData()
        ingredients.ingredientNames = nowData
        listeners.forEach { it.onFinish(ingredients) }
    }
}
interface OnTaskFinish{
    fun onFinish(data: IngredientData)
}