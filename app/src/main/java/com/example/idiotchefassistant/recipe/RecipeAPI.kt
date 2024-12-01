package com.example.idiotchefassistant.recipe

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeAPI {
    @GET("/recipes/content/{rid}")
    fun getRecipeContent(
        @Path("rid") rid: Int
    ): Call<RecipeData>
}