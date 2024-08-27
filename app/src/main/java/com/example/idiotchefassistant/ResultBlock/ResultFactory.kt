package com.example.idiotchefassistant.ResultBlock

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultFactory(val resultRepository: ResultRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ResultViewModel::class.java)){
            return ResultViewModel(resultRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}