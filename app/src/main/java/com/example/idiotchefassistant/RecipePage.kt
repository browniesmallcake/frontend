package com.example.idiotchefassistant

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.idiotchefassistant.databinding.ActivityRecipePageBinding

class RecipePage : AppCompatActivity() {
    private lateinit var binding: ActivityRecipePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = generateFakeData(10)
        val commentSection = binding.commentSection

        items.forEach {it ->
            val commentView = LayoutInflater.from(this).inflate(R.layout.recipe_commit_item, commentSection, false)
            val userImage = commentView.findViewById<ImageView>(R.id.user_image)
            val userName = commentView.findViewById<TextView>(R.id.user_name)
            val userReview = commentView.findViewById<TextView>(R.id.user_review)
            val userComment = commentView.findViewById<TextView>(R.id.user_comment)

            userImage.setImageResource(it.userImg)
            userName.text = it.userName
            userComment.text = it.commit
            userReview.text = it.review.toString()

            commentSection.addView(commentView)
        }
    }
    private fun generateFakeData(cnt: Int): List<CommitItem> {
        val fakeData = mutableListOf<CommitItem>()
        for (i in 1..cnt) {
            fakeData.add(CommitItem(R.drawable.logo, "用戶 $i", "這是一個評論", 5))
        }
        return fakeData
    }
}

