package com.example.idiotchefassistant.recipeBlock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel(private var searchRepository: SearchRepository): ViewModel() {
    private var userLiveData = MutableLiveData<SearchData>()

    fun callBack(): LiveData<SearchData> {
        searchRepository.loadData(object : SearchRepository.OnTaskFinish {
            override fun onFinish(data: SearchData){
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }


}