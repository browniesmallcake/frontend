package com.example.idiotchefassistant.itemBlock

import java.util.concurrent.Executors

class IngredientRepository {
    private var nowDatas = arrayOf("beef", "chicken", "pork", "tomato", "banana", "potato", "egg")
    fun loadData(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val ingredients = IngredientData()
            ingredients.ingredientNames = nowDatas
//            Thread.sleep(500)
            task.onFinish(ingredients)
        }
    }

    fun getDatas(): Array<String> {
        return nowDatas
    }

    fun setData(newData: Array<String>) {
        nowDatas = newData
    }
}

interface OnTaskFinish{
    fun onFinish(data: IngredientData)
}