package com.example.idiotchefassistant.ResultBlock

import android.app.ProgressDialog
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idiotchefassistant.ItemBlock.IngredientDialogFragment
import com.example.idiotchefassistant.ItemBlock.IngredientItem
import com.example.idiotchefassistant.ItemBlock.IngredientItemAdapter
import com.example.idiotchefassistant.RecipeBlock.SearchPage
import com.example.idiotchefassistant.databinding.ActivityResultPageBinding
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ResultPage : AppCompatActivity(), IngredientItemAdapter.OnItemClickListener {
    private lateinit var binding: ActivityResultPageBinding
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var resultFactory: ResultFactory
    private lateinit var resultRepository: ResultRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResultPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultRepository = ResultRepository()
        resultFactory = ResultFactory(resultRepository)
        resultViewModel = ViewModelProviders.of(this, resultFactory).get(ResultViewModel::class.java)
        val video = intent.getStringExtra("videoUri")

        // upload video
        val videoFile = File("${video}")
        Log.i("videoFile", "Video file is exist: ${videoFile.exists()}")
//        val fbody = videoFile.asRequestBody()
//        retrofitClient.detectAPI.detect(fbody)

        // get the data from server
        val dialog = ProgressDialog.show(
            this, "",
            "Loading. Please wait...", true
        )
        dialog.show()
        resultViewModel.callResult().observe(this, Observer {
            dialog.dismiss()
            Toast.makeText(this, "name:${it.resultName}", Toast.LENGTH_SHORT).show()
        })

        binding.addButton.setOnClickListener {
            IngredientDialogFragment().show(supportFragmentManager, "customDialog")
        }

        val items = generateFakeData(10)
        val recycleView = binding.recyclerViewIngredients
        recycleView.layoutManager = LinearLayoutManager(this)

        val adapter = IngredientItemAdapter(items)
        adapter.setOnItemClickListener(this)
        recycleView.adapter = adapter

        binding.searchButton.setOnClickListener {
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(item: IngredientItem) {
        IngredientDialogFragment().show(supportFragmentManager, "customDialog")
    }

    private fun generateFakeData(cnt: Int): List<IngredientItem> {
        return List(cnt) { IngredientItem("Item ${it + 1}") }
    }

}

