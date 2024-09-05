package com.example.idiotchefassistant.itemBlock

import android.util.Log
import java.util.concurrent.Executors

class IngredientRepository {
    private var nowDatas = arrayOf("asparagus", "avocado", "bamboo_shoots", "beans_green", "beetroot", "cassava", "chayote", "cinnamon", "coriander", "corn", "egg", "bean_mung", "cabbage_napa", "carrot", "chicken", "crab", "garlic", "mint", "pepper_bell", "potato", "chili", "eggplant", "gourd_bitter", "gourd_bottle", "gourd_pointed", "ham", "jackfruit", "lemon", "mushroom_enoki", "onion", "pork", "potato_sweet", "rice", "almond", "apple", "artichoke", "banana", "blueberry", "broccoli", "broccoli_white", "mustard_greens", "spinach", "turnip", "butter", "cheese", "meat", "milk", "pasta", "strawberry", "ash_gourd", "beans_red", "bokchoy", "bread", "brocolli_chinese", "cabbage", "cucumber", "edamame", "fish", "mushroom", "noodle", "okra", "oyster", "pumpkin", "radish", "seaweed", "taro", "tomato", "tomato_cherry", "clam", "burdock", "peanut", "spinach_water", "leek", "gourd_sponge", "salmon", "apple_wax", "chives", "coconut", "dragon_fruit", "duck", "durian", "frog", "ginger", "grape", "guava", "heim", "kiwi", "lettuce", "mango", "melon_water", "orange", "papaya", "passion_fruit", "pineapple", "potato_leaves", "prawn", "spinach_chinese", "squid", "tofu", "zuccini")
    private val listeners = mutableListOf<OnTaskFinish>()

    fun loadData(task: OnTaskFinish) {
        listeners.add(task)
        Executors.newSingleThreadExecutor().submit {
            val ingredients = IngredientData()
            ingredients.ingredientNames = nowDatas
            task.onFinish(ingredients)
        }
    }
    fun getDatas(): Array<String> {
        return nowDatas
    }
    fun setData(newData: Array<String>) {
        nowDatas = newData
        nowDatas.forEach {
            Log.i("", it)
        }
        notifyListeners()
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