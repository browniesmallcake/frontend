package com.example.idiotchefassistant.recipeBlock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipeViewModel(private var recipeRepository: RecipeRepository):ViewModel() {
    private var userLiveData = MutableLiveData<RecipeData>()

    fun callBack(): LiveData<RecipeData> {
        recipeRepository.loadData(object : RecipeRepository.OnTaskFinish {
            override fun onFinish(data: RecipeData) {
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }

    fun link2Image(link: String?): String {
        val videoId = link.let {
            val regex = "https?://(?:www\\.)?youtube\\.com/watch\\?v=([\\w-]+)".toRegex()
            val matchResult = it?.let { it1 -> regex.find(it1) }
            matchResult?.groups?.get(1)?.value
        }
        var imageLink = ""
        if (videoId != null) {
            imageLink = "https://img.youtube.com/vi/${videoId}/hqdefault.jpg"
        }
        return imageLink
    }

}