package com.example.idiotchefassistant.recipeBlock

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ResultSearchAPI {
    @GET("/recipes/search/iid")
    fun search(@Query("offset") offset: Int, @Query("iids") iids: List<Int>): Call<List<RecipeItem>>
}