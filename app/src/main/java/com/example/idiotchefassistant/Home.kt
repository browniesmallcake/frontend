package com.example.idiotchefassistant

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idiotchefassistant.databinding.ActivityHomeBinding
import com.example.idiotchefassistant.ui.login.LoginActivity

class Home : AppCompatActivity(), RecipeItemAdapter.OnItemClickListener{
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*        val headerFragment = PageHeader()
        supportFragmentManager.beginTransaction()
            .replace(R.id.header_fragment, headerFragment)
            .commit()*/

        binding.DrawerLayout.visibility = View.GONE

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                }
                R.id.nav_history -> {
                    val intent = Intent(this, HistoryPage::class.java)
                    startActivity(intent)
                    binding.DrawerLayout.visibility = View.GONE
                }
                R.id.nav_info -> {
                    //点击事件
                }
                R.id.nav_setting -> {
                    //点击事件
                }
                R.id.nav_login -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    binding.DrawerLayout.visibility = View.GONE
                }
            }
            binding.DrawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val toggle = ActionBarDrawerToggle(
            this,
            binding.DrawerLayout,
            binding.toolbar,
            0,
            0
        )
        binding.DrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.SearchBtn.setOnClickListener {
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }
        binding.SettingBtn.setOnClickListener {
            if (binding.DrawerLayout.visibility == View.VISIBLE) {
                binding.DrawerLayout.visibility = View.GONE
            } else {
                binding.DrawerLayout.visibility = View.VISIBLE
            }
        }

        binding.CameraBtn.setOnClickListener {
            val intent = Intent(this, CameraPage::class.java)
            startActivity(intent)
        }
        val items = generateFakeData(100)
        val recycleView = binding.RecipeRecycleView
        recycleView.layoutManager = LinearLayoutManager(this)

        val adapter = RecipeItemAdapter(items)
        adapter.setOnItemClickListener(this)
        recycleView.adapter = adapter
    }

    private fun generateFakeData(count: Int): List<RecipeItem> {
        val fakeData = mutableListOf<RecipeItem>()
        for (i in 1..count) {
            fakeData.add(RecipeItem("食譜 $i", "食譜 $i 的描述", 5))
        }
        return fakeData
    }

    override fun onItemClick(item: RecipeItem) {
        val intent = Intent(this, RecipePage::class.java)
        startActivity(intent)
    }
}