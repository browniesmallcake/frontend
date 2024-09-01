package com.example.idiotchefassistant.resultBlock

import DetectAPI
import retrofit2.Retrofit

var retrofit = Retrofit.Builder()
    .baseUrl("http://120.107.172.139:8000/")
    .build()
val detectService = retrofit.create(DetectAPI::class.java)