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

class ResultPage : AppCompatActivity(), ResultItemAdapter.OnItemClickListener, IngredientDialogFragment.OnItemSelectedListener {
    private lateinit var binding: ActivityResultPageBinding
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var resultFactory: ResultFactory
    private lateinit var resultRepository: ResultRepository

    private lateinit var adapter: ResultItemAdapter
    private var isEditMode = false
    private var editItemTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResultPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultRepository = ResultRepository()
        resultFactory = ResultFactory(resultRepository)
        resultViewModel = ViewModelProviders.of(this, resultFactory).get(ResultViewModel::class.java)

        val video = intent.getStringExtra("videoUri")
        resultViewModel.uploadVideo(this, video)

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
            val dialog = IngredientDialogFragment()
            dialog.setOnItemSelectedListener(this)
            isEditMode = false
            dialog.show(supportFragmentManager, "customDialog")
        }

        binding.searchButton.setOnClickListener {
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }

    }

    override fun onEditClick(item: ResultItem) {
        val dialog = IngredientDialogFragment()
        dialog.setOnItemSelectedListener(this)
        isEditMode = true
        editItemTitle = item.Title
        dialog.show(supportFragmentManager, "customDialog")
    }

    override fun onItemSelected(itemName: String?) {
        if (itemName != null) {
            if (isEditMode) {
                if (resultViewModel.findData(itemName)) {
                    AlertDialog.Builder(this)
                        .setTitle("Item Exist Already")
                        .setMessage("The item \"${itemName}\" does exist in the list.")
                        .setPositiveButton("OK", null)
                        .show()
                } else {
                    resultViewModel.editData(editItemTitle!!, itemName)
                }
            } else {
                if (resultViewModel.findData(itemName)) {
                    AlertDialog.Builder(this)
                        .setTitle("Item Exist Already")
                        .setMessage("The item \"${itemName}\" does exist in the list.")
                        .setPositiveButton("OK", null)
                        .show()
                } else {
                    resultViewModel.addData(
                        itemName,
                        "app/src/main/res/drawable/logo.png"
                    ) // Pass the image path here
                }
            }
        }
        adapter.updateItems(resultRepository.getDatas()?.map { ResultItem(it.value, it.key) } ?: emptyList())
        adapter.notifyDataSetChanged()
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
                adapter.updateItems(resultRepository.getDatas()?.map { ResultItem(it.value, it.key) } ?: emptyList())
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }
}

