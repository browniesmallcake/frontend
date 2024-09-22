package com.example.idiotchefassistant.resultBlock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors

class ResultRepository {
    private var nowData: MutableLiveData<Map<String, ArrayList<String>>> = MutableLiveData(emptyMap())
    fun loadData(task: OnTaskFinish): LiveData<Map<String, ArrayList<String>>> {
        Executors.newSingleThreadExecutor().submit {
            val results = ResultData()
            results.result = nowData.value
//            Thread.sleep(3000)
            task.onFinish(results)
        }
        return nowData
    }

    fun uploadData(newResults: Map<String, ArrayList<String>>) {
        nowData.value = newResults
//        Log.i("ingredients name: ", newResults.keys.toString())
    }

    fun getData(): Map<String, ArrayList<String>>? {
        return nowData.value
    }

    interface OnTaskFinish{
        fun onFinish(data: ResultData)
    }
}
