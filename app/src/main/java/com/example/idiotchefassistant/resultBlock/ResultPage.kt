package com.example.idiotchefassistant.resultBlock

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idiotchefassistant.itemBlock.IngredientDialogFragment
import com.example.idiotchefassistant.itemBlock.IngredientItem
import com.example.idiotchefassistant.itemBlock.IngredientItemAdapter
import com.example.idiotchefassistant.recipeBlock.SearchPage
import com.example.idiotchefassistant.databinding.ActivityResultPageBinding
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
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
        resultViewModel.upload(video)

        // get the data from server
        val recyclerView = binding.recyclerViewIngredients
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = IngredientItemAdapter(emptyList())
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter

        val dialog = ProgressDialog.show(
            this, "",
            "Loading. Please wait...", true
        )
        dialog.show()
        resultViewModel.callBack().observe(this, Observer {
            dialog.dismiss()
//            Toast.makeText(this, "name:${it.resultName}", Toast.LENGTH_SHORT).show()
            val items = it.resultNames?.map { name ->
                IngredientItem(name)
            } ?: emptyList()
            adapter.updateItems(items)
        })

        binding.addButton.setOnClickListener {
            IngredientDialogFragment().show(supportFragmentManager, "customDialog")
        }

        binding.searchButton.setOnClickListener {
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }

    }

    override fun onItemClick(item: IngredientItem) {
        IngredientDialogFragment().show(supportFragmentManager, "customDialog")
    }
}

