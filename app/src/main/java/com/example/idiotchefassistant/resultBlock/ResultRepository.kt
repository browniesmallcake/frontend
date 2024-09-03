package com.example.idiotchefassistant.resultBlock
import java.util.concurrent.Executors

class ResultRepository {
    private var nowDatas: Map<String, ArrayList<String>>? = mapOf("beef" to arrayListOf("app/src/main/res/drawable/logo.png"))
    fun loadData(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val results = ResultData()
            results.result = nowDatas
//            Thread.sleep(3000)
            task.onFinish(results)
        }
    }

    fun uploadData(newResults: Map<String, ArrayList<String>>) {
        nowDatas = newResults
    }

    fun getDatas(): Map<String, ArrayList<String>>? {
        return nowDatas
    }
}

interface OnTaskFinish{
    fun onFinish(data: ResultData)
}