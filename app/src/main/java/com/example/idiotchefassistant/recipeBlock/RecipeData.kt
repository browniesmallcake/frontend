package com.example.idiotchefassistant.recipeBlock

class RecipeData {
    val title: String = "Unknown"
    //    val author: String,
    val description: String = ""
    val video: String = ""
    val rtype: String = ""
    val score: Float? = null
    val comments: ArrayList<Comment>? = null
    val iids: ArrayList<Int> = arrayListOf()
}

data class Comment(
    val name: String,
    val content: String,
    val review: Int
)