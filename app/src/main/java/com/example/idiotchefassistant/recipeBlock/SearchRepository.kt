package com.example.idiotchefassistant.recipeBlock

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors

class SearchRepository {
    private var nowData: MutableLiveData<SearchData> = MutableLiveData(SearchData())
    fun loadData(task: OnTaskFinish): MutableLiveData<SearchData>{
        Executors.newSingleThreadExecutor().submit{
            val data = SearchData()
            data.list = nowData.value?.list
            task.onFinish(data)
        }
        return nowData
    }

    interface OnTaskFinish{
        fun onFinish(data: SearchData)
    }
}