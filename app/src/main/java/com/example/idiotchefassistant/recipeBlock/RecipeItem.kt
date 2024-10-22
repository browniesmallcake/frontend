package com.example.idiotchefassistant.recipeBlock

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeItem(
    val rid:Int,
    val title:String,
    val author:String?,
    val description:String?,
    val rType:String?
): Parcelable
data class CommitItem(val userImg: Int, val userName:String, val commit:String, val review:Int)
