package com.example.idiotchefassistant.itemBlock

import android.util.Log
import java.util.concurrent.Executors

class IngredientRepository {
    private var nowDatas = emptyArray<String>()
    private val listeners = mutableListOf<OnTaskFinish>()

    fun loadData(task: OnTaskFinish) {
        listeners.add(task)
        Executors.newSingleThreadExecutor().submit {
            val ingredients = IngredientData()
            ingredients.ingredientNames = nowDatas
            task.onFinish(ingredients)
            Log.i("ingredients name in repository: ", nowDatas.joinToString(", "))
        }
    }
    fun getDatas(): Array<String> {
        return nowDatas
    }
    fun setData(newData: Array<String>) {
        nowDatas = newData
        notifyListeners()
//        Log.i("ingredients name in repository: ", nowDatas.joinToString(", "))
    }
    private fun notifyListeners() {
        val ingredients = IngredientData()
        ingredients.ingredientNames = nowDatas
        listeners.forEach { it.onFinish(ingredients) }
    }
}
interface OnTaskFinish{
    fun onFinish(data: IngredientData)
}