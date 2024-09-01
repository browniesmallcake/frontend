package com.example.idiotchefassistant.resultBlock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel(private var resultRepository: ResultRepository): ViewModel() {
    private var userLiveData = MutableLiveData<ResultData>()

    fun callBack():LiveData<ResultData>{
        resultRepository.loadResult(object: OnTaskFinish{
            override fun onFinish(data: ResultData){
                userLiveData.postValue(data)
            }
        })
        return userLiveData
    }
}