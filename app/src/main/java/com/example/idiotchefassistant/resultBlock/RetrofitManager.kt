package com.example.idiotchefassistant.resultBlock

import DetectAPI
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var retrofit = Retrofit.Builder()
    .baseUrl("http://120.107.172.139:8000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val detectService = retrofit.create(DetectAPI::class.java)