package com.example.idiotchefassistant

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.idiotchefassistant.databinding.ActivityCameraPageBinding

class CameraPage : AppCompatActivity() {
    private lateinit var binding: ActivityCameraPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_camera_page)
        binding = ActivityCameraPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BackBtn.setOnClickListener{
            finish()
        }
    }
}