package com.example.idiotchefassistant.itemBlock

import java.util.concurrent.Executors

class IngredientRepository {
    private var ingredientList  = emptyArray<String>()
    private var seasoningList   = arrayOf("醬油 soy oil", "番茄醬 ketchup")
    private var nowData = ingredientList
    private val listeners = mutableListOf<OnTaskFinish>()

    fun loadData(task: OnTaskFinish) {
        listeners.add(task)
        Executors.newSingleThreadExecutor().submit {
            val ingredients = IngredientData()
            ingredients.ingredientNames = nowData
            task.onFinish(ingredients)
        }
    }
    fun getData(): Array<String> {
        return nowData
    }
    fun setData(newData: Array<String>, isSeason: Boolean = false) {
        if(isSeason)
            seasoningList = newData
        else
            ingredientList = newData
        nowData = if(isSeason) seasoningList else ingredientList
        notifyListeners()
    }

    fun switchData(isSeason: Boolean){
        nowData = if(isSeason) seasoningList else ingredientList
        notifyListeners()
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