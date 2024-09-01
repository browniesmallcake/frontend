package com.example.idiotchefassistant.resultBlock

import java.util.concurrent.Executors

class ResultRepository {
    fun loadResult(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val results = ResultData()
            results.resultNames = arrayOf("beef", "chicken", "pork")
            Thread.sleep(3000)
            task.onFinish(results)
        }
    }
}

interface OnTaskFinish{
    fun onFinish(data: ResultData)
}