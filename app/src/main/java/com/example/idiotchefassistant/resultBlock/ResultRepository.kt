package com.example.idiotchefassistant.resultBlock
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors

class ResultRepository {
    private var nowData: MutableLiveData<ResultData> = MutableLiveData(ResultData())
    fun loadData(task: OnTaskFinish): MutableLiveData<ResultData> {
        Executors.newSingleThreadExecutor().submit {
            val results = ResultData()
            results.result = nowData.value?.result
            task.onFinish(results)
        }
        return nowData
    }

    fun uploadData(newData: ResultData) {
        nowData.value = newData
    }

    fun getData(): ResultData? {
        return nowData.value
    }

    interface OnTaskFinish{
        fun onFinish(data: ResultData)
    }
}
