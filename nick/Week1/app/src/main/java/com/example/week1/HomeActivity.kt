package com.example.week1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.week1.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val text = "hello toast"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, text, duration)

        val intent = Intent(this, JoyActivity::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.homeJoyIv.setOnClickListener {
            binding.homeJoyTv.setTextColor(Color.YELLOW)
            toast.show()
            startActivity(intent)
        }

        binding.homeExcitedIv.setOnClickListener {
            binding.homeExcitedTv.setTextColor(Color.BLUE)
            toast.show()
        }

        binding.homeSosoIv.setOnClickListener {
            binding.homeSosoTv.setTextColor(Color.CYAN)
            toast.show()
        }

        binding.homeNervousIv.setOnClickListener {
            binding.homeNervousTv.setTextColor(Color.GREEN)
            toast.show()
        }

        binding.homeMadIv.setOnClickListener {
            binding.homeNervousTv.setTextColor(Color.RED)
            toast.show()
        }
    }
}