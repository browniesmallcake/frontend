package com.example.idiotchefassistant.ResultBlock

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel(var resultRepository: ResultRepository): ViewModel() {
    private var userResultLiveData = MutableLiveData<ResultData>()

    fun callResult():LiveData<ResultData>{
        resultRepository.loadResult(object: OnTaskFinish{
            override fun onFinish(data: ResultData){
                userResultLiveData.postValue(data)
            }
        })
        return userResultLiveData
    }
}