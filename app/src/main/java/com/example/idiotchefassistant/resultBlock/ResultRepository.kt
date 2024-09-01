package com.example.idiotchefassistant.resultBlock

import java.util.concurrent.Executors

class ResultRepository {
    private var nowResults: Array<String>? = arrayOf("beef")
    fun loadResult(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val results = ResultData()
            results.resultNames = nowResults
            Thread.sleep(3000)
            task.onFinish(results)
        }
    }

    fun uploadResult(newResults: Array<String>) {
        nowResults = newResults
    }

    fun getNowResults(): Array<String>? {
        return nowResults
    }
}

interface OnTaskFinish{
    fun onFinish(data: ResultData)
}