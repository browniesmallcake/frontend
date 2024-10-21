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

    fun uploadData(newResults: ResultData) {
        nowData.value = newResults
//        Log.i("ingredients name: ", newResults.keys.toString())
    }

    fun getData(): ResultData? {
        return nowData.value
    }

    interface OnTaskFinish{
        fun onFinish(data: ResultData)
    }
}
