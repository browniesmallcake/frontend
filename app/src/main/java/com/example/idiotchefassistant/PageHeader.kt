package com.example.idiotchefassistant

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.idiotchefassistant.databinding.FragmentHeaderBinding
import com.example.idiotchefassistant.ui.login.LoginActivity

class PageHeader : Fragment() {

    private var _binding: FragmentHeaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeaderBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.DrawerLayout.visibility = View.GONE

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                }
                R.id.nav_history -> {
                    //点击事件
                }
                R.id.nav_info -> {
                    //点击事件
                }
                R.id.nav_setting -> {
                    //点击事件
                }
                R.id.nav_login -> {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    binding.DrawerLayout.visibility = View.GONE
                }
            }
            binding.DrawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.DrawerLayout,
            binding.toolbar,
            0,
            0
        )
        binding.DrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.SearchBtn.setOnClickListener {
            val intent = Intent(requireContext(), SearchPage::class.java)
            startActivity(intent)
        }
        binding.SettingBtn.setOnClickListener {
            if (binding.DrawerLayout.visibility == View.VISIBLE) {
                binding.DrawerLayout.visibility = View.GONE
            } else {
                binding.DrawerLayout.visibility = View.VISIBLE
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
