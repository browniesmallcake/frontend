package com.example.idiotchefassistant.itemBlock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idiotchefassistant.resultBlock.ingredientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientViewModel(private var ingredientRepository: IngredientRepository): ViewModel() {
    private var userLiveData = MutableLiveData<IngredientData>()

    fun callBack(): LiveData<IngredientData> {
        ingredientRepository.loadData(object : OnTaskFinish {
            override fun onFinish(data: IngredientData) {
                if(data.ingredientNames?.isNotEmpty() == true){
                    userLiveData.postValue(data)
                }
                else{
                    Log.i("Ingredient ViewModel", "No data received or empty")
                }
            }
        })
        return userLiveData
    }

    fun switchData(isSeason: Boolean){
        ingredientRepository.switchData(isSeason)
    }

    fun setData(newData: Array<String>) {
        ingredientRepository.setData(newData)
        val ingredientData = IngredientData()
        if (newData.isNotEmpty()) {
            ingredientData.ingredientNames = newData
            userLiveData.postValue(ingredientData)
        } else {
            Log.i("ViewModel", "Received empty data")
        }
    }

    fun filterItems(query: String) {
        val allItems = ingredientRepository.getData()
        val data = IngredientData()
        data.ingredientNames = allItems.filter { it.contains(query, ignoreCase = true) }.toTypedArray()
        userLiveData.postValue(data)
    }

    fun getData() {
        // get ingredient list
        ingredientService.getList().enqueue(object : Callback<ArrayList<IngredientItem>> {
            override fun onResponse(
                call: Call<ArrayList<IngredientItem>>,
                response: Response<ArrayList<IngredientItem>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()
                    val names: Array<String>? = list?.map { it.name.replace("_", " ") }?.toTypedArray()
                    val mandarins: Array<String>? = list?.map {it.mandarin}?.toTypedArray()
                    Log.i("onResponse3","OK")
                    if (names != null && mandarins != null) {
                        val zipArray = names.zip(mandarins) {name, mandarin -> "$mandarin $name"}.toTypedArray()
                        setData(zipArray)
                    }
                }
                ingredientRepository.loadData(object: OnTaskFinish{
                    override fun onFinish(data: IngredientData){
                        userLiveData.postValue(data)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<IngredientItem>>, t: Throwable) {
                Log.i("onFailure3",t.toString())
            }

        })
    }
}