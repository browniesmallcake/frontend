package com.example.idiotchefassistant.ResultBlock

import DetectAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val retrofitClient = RetrofitManager()

class RetrofitManager internal constructor() {

    val detectAPI: DetectAPI

    init {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS) // 設置連線Timeout
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://120.107.172.139:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        this.detectAPI = retrofit.create(DetectAPI::class.java)
    }
}