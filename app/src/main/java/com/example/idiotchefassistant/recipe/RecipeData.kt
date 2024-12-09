package com.example.idiotchefassistant.recipe

class RecipeData {
    val title: String = "Unknown"
    //    val author: String,
    val description: String? = null
    val video: String? = null
    val rtype: String? = null
    val score: Float = 0.toFloat()
    val comments: ArrayList<Comment>? = null
    val iids: ArrayList<Int> = arrayListOf()
}

data class Comment(
    val username: String,
    val content: String,
    val score: Int
)