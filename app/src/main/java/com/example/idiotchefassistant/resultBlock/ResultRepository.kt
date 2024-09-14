package com.example.idiotchefassistant.resultBlock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors

class ResultRepository {
    private var nowDatas: MutableLiveData<Map<String, String>> = MutableLiveData(emptyMap())
    fun loadData(task: OnTaskFinish): LiveData<Map<String, String>> {
        Executors.newSingleThreadExecutor().submit {
            val results = ResultData()
            results.result = nowDatas.value
//            Thread.sleep(3000)
            task.onFinish(results)
        }
        return nowDatas
    }

    fun uploadData(newResults: Map<String, String>) {
        nowDatas.value = newResults
    }

    fun getDatas(): Map<String, String>? {
        return nowDatas.value
    }

    interface OnTaskFinish{
        fun onFinish(data: ResultData)
    }
}
