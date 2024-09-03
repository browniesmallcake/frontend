package com.example.idiotchefassistant.resultBlock

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AlertDialog
import com.example.idiotchefassistant.itemBlock.IngredientDialogFragment
import com.example.idiotchefassistant.recipeBlock.SearchPage
import com.example.idiotchefassistant.databinding.ActivityResultPageBinding

class ResultPage : AppCompatActivity(), ResultItemAdapter.OnItemClickListener {
    private lateinit var binding: ActivityResultPageBinding
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var resultFactory: ResultFactory
    private lateinit var resultRepository: ResultRepository

    private lateinit var adapter: ResultItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResultPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultRepository = ResultRepository()
        resultFactory = ResultFactory(resultRepository)
        resultViewModel = ViewModelProviders.of(this, resultFactory).get(ResultViewModel::class.java)

        val video = intent.getStringExtra("videoUri")
        resultViewModel.uploadVideo(video)

        // get the data from server
        val recyclerView = binding.recyclerViewIngredients
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ResultItemAdapter(emptyList())
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter

        val dialog = ProgressDialog.show(
            this, "",
            "Loading. Please wait...", true
        )
        dialog.show()
        resultViewModel.callBack().observe(this, Observer {
            dialog.dismiss()
            val items = it.result?.map { entry ->
                ResultItem(entry.value, entry.key)
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

    override fun onEditClick(item: ResultItem) {
        // 跳轉到 IngredientDialogFragment
        IngredientDialogFragment().show(supportFragmentManager, "customDialog")
    }

    override fun onDeleteClick(item: ResultItem) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { _, _ ->
                if(!resultViewModel.findData(item.Title)){
                    AlertDialog.Builder(this)
                    .setTitle("Item Not Found")
                    .setMessage("The item \"${item.Title}\" does not exist in the list.")
                    .setPositiveButton("OK", null)
                    .show()
                }
                else {
                    resultViewModel.deleteData(item.Title)
                }
                adapter.updateItems(resultRepository.getNowResults()?.map { ResultItem(it.value, it.key) } ?: emptyList())
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }
}

