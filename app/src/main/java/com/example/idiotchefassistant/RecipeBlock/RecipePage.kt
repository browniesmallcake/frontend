package com.example.idiotchefassistant.RecipeBlock

import android.os.Bundle
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

/*        val items = generateFakeData(10)
        val commentSection = binding.commentSection

        items.forEach {
            val commentView = LayoutInflater.from(this).inflate(R.layout.recipe_commit_item, commentSection, false)
            if (commentView != null) {
                val userImage = commentView.findViewById<ImageView>(R.id.user_image)
                val userName = commentView.findViewById<TextView>(R.id.user_name)
                val userComment = commentView.findViewById<TextView>(R.id.user_comment)
                val userReview = commentView.findViewById<TextView>(R.id.user_review)

                userImage.setImageResource(it.userImg)
                userName.text = it.userName
                userComment.text = it.commit
                userReview.text = it.review.toString()

                commentSection.addView(commentView)
            } else {
                Log.e("RecipePage", "commentView is null")
            }
        }*/
    }
/*    private fun generateFakeData(cnt: Int): List<CommitItem> {
        val fakeData = mutableListOf<CommitItem>()
        for (i in 1..cnt) {
            fakeData.add(CommitItem(R.drawable.logo, "用戶 $i", "這是一個評論", 5))
        }
        return fakeData
    }*/
}

