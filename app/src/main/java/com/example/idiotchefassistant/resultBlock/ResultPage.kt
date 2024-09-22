package com.example.idiotchefassistant.resultBlock

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.idiotchefassistant.itemBlock.IngredientDialogFragment
import com.example.idiotchefassistant.recipeBlock.SearchPage
import com.example.idiotchefassistant.databinding.ActivityResultPageBinding
import com.example.idiotchefassistant.itemBlock.IngredientFactory
import com.example.idiotchefassistant.itemBlock.IngredientRepository
import com.example.idiotchefassistant.itemBlock.IngredientViewModel

class ResultPage : AppCompatActivity(), ResultItemAdapter.OnItemClickListener, IngredientDialogFragment.OnItemSelectedListener {
    private lateinit var binding: ActivityResultPageBinding
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var resultFactory: ResultFactory
    private lateinit var resultRepository: ResultRepository
    private lateinit var ingredientViewModel:IngredientViewModel
    private lateinit var ingredientFactory: IngredientFactory
    private lateinit var ingredientRepository: IngredientRepository
    private lateinit var progressDialog: ProgressDialog

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
        resultViewModel = ViewModelProvider(this, resultFactory)[ResultViewModel::class.java]
        ingredientRepository = IngredientRepository()
        ingredientFactory = IngredientFactory(ingredientRepository)
        ingredientViewModel = ViewModelProvider(this, ingredientFactory)[IngredientViewModel::class.java]

        val video = intent.getStringExtra("videoUri")
        resultViewModel.uploadVideo(video)

        // get the data from server
        val recyclerView = binding.recyclerViewIngredients
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ResultItemAdapter(emptyList())
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
        resultViewModel.callBack().observe(this) {
            val items = it.result?.map { entry ->
                ResultItem(entry.value, entry.key)
            } ?: emptyList()
            adapter.updateItems(items)
        }
        progressDialog = ProgressDialog(this).apply {
            setMessage("Uploading video...")
            setCancelable(false)
        }
        resultViewModel.isUploading.observe(this) { isUploading ->
            if (isUploading) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }
        resultViewModel.uploadResult.observe(this) { isSuccess ->
            if (!isSuccess) {
                AlertDialog.Builder(this)
                    .setTitle("Upload Failed")
                    .setMessage("The video upload failed, please try again.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        binding.addButton.setOnClickListener {
            val dialog = IngredientDialogFragment()
            dialog.setOnItemSelectedListener(this)
            isEditMode = false
            dialog.show(supportFragmentManager, "customDialog")
            adapter.notifyDataSetChanged()
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
        editItemTitle = item.title
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
    }

    override fun onDeleteClick(item: ResultItem) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { _, _ ->
                if(!resultViewModel.findData(item.title)){
                    AlertDialog.Builder(this)
                    .setTitle("Item Not Found")
                    .setMessage("The item \"${item.title}\" does not exist in the list.")
                    .setPositiveButton("OK", null)
                    .show()
                }
                else {
                    resultViewModel.deleteData(item.title)
                }
                adapter.updateItems(resultRepository.getDatas()?.map { ResultItem(it.value, it.key) } ?: emptyList())
            }
            .setNegativeButton("No", null)
            .show()
    }
}

