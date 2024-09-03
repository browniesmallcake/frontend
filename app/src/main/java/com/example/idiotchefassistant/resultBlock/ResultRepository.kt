package com.example.idiotchefassistant.resultBlock
import java.util.concurrent.Executors

class ResultRepository {
    private var nowResults: Map<String, ArrayList<String>>? = mapOf("beef" to arrayListOf("app/src/main/res/drawable/logo.png"))
    fun loadResult(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val results = ResultData()
            results.result = nowResults
//            Thread.sleep(3000)
            task.onFinish(results)
        }
    }

    fun uploadResult(newResults: Map<String, ArrayList<String>>) {
        nowResults = newResults
    }

    fun getNowResults(): Map<String, ArrayList<String>>? {
        return nowResults
    }
}

interface OnTaskFinish{
    fun onFinish(data: ResultData)
}