package com.example.idiotchefassistant.ResultBlock

import java.util.concurrent.Executors

class ResultRepository {
    fun loadResult(task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit{
            val results = ResultData()
            results.resultName = "beef"
            Thread.sleep(3000)
            task.onFinish(results)
        }
    }
}

interface OnTaskFinish{
    fun onFinish(data: ResultData)
}