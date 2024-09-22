package com.example.idiotchefassistant.resultBlock

import DetectAPI
import com.example.idiotchefassistant.itemBlock.IngredientAPI
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS) // 設置連接超時時間
    .readTimeout(60, TimeUnit.SECONDS)    // 設置讀取超時時間
    .writeTimeout(60, TimeUnit.SECONDS)   // 設置寫入超時時間
    .build()

var retrofit = Retrofit.Builder()
    .baseUrl("https://topic114.bntw.dev/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

val detectService = retrofit.create(DetectAPI::class.java)

val ingredientService = retrofit.create(IngredientAPI::class.java)