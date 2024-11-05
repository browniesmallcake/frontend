package com.example.idiotchefassistant.recipeBlock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idiotchefassistant.recipeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class RecipeRepository {
    private var liveData: MutableLiveData<RecipeData> = MutableLiveData(RecipeData())

    fun loadData(task: OnTaskFinish): MutableLiveData<RecipeData>{
        Executors.newSingleThreadExecutor().submit{
            var data = RecipeData()
            data = liveData.value!!
            task.onFinish(data)
        }
        return liveData
    }

    fun getRecipeContent(rid: Int): LiveData<RecipeData>{
        val liveData = MutableLiveData<RecipeData>()
        recipeService.getRecipeContent(rid).enqueue(object: Callback<RecipeData> {
            override fun onResponse(
                call: Call<RecipeData>,
                response: Response<RecipeData>
            ){
                if(response.isSuccessful){
                    liveData.postValue(response.body())
                    Log.i("getRecipeContent", "rid: $rid\n" +
                            "title: ${response.body()?.title}\n" +
                            "video: ${response.body()?.video}\n" +
                            "descr: ${response.body()?.description}\n" +
                            "score: ${response.body()?.score}\n" +
                            "rtype: ${response.body()?.rtype}\n" +
                            "iids : ${response.body()?.iids}\n" +
                            "comts: ${response.body()?.comments}\n")
                }
                else{
                    liveData.postValue(RecipeData())
                    Log.e("getRecipeContent", "Failed:${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecipeData>, t: Throwable){
                Log.e("getRecipeContent", "API call failed: ${t.message}")
                liveData.postValue(RecipeData())
            }
        })
        return liveData
    }

    interface OnTaskFinish{
        fun onFinish(data: RecipeData)
    }
}