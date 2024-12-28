package com.example.idiotchefassistant.recipe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeItem(
    val rid:Int,
    val title:String,
    val link:String,
    val score:Float
): Parcelable

//data class CommitItem(val userImg: Int, val userName:String, val context:String, val review:Int)
