package com.example.idiotchefassistant

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idiotchefassistant.databinding.FragmentHomePageBinding

class HomePage : Fragment(), RecipeItemAdapter.OnItemClickListener {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.CameraBtn.setOnClickListener {
            val intent = Intent(activity, CameraPage::class.java)
            startActivity(intent)
        }

        val items = generateFakeData(10)
        val recycleView = binding.RecipeRecycleView
        recycleView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = RecipeItemAdapter(items)
        adapter.setOnItemClickListener(this)
        recycleView.adapter = adapter

        return view
    }
    private fun generateFakeData(cnt: Int): List<RecipeItem> {
        val fakeData = mutableListOf<RecipeItem>()
        for (i in 1..cnt) {
            fakeData.add(RecipeItem("食譜 $i", "食譜 $i 的描述", 5))
        }
        return fakeData
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: RecipeItem){
        val intent = Intent(activity, RecipePage::class.java)
        startActivity(intent)
    }

}