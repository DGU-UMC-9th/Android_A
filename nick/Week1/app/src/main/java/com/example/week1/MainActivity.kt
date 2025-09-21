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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val text = "hello toast"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, text, duration)

        val intent = Intent(this, MainActivity2::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.joyImage.setOnClickListener {
            binding.joyText.setTextColor(Color.YELLOW)
            toast.show()
            startActivity(intent)
        }

        binding.exitedImage.setOnClickListener {
            binding.exitedText.setTextColor(Color.BLUE)
            toast.show()
        }

        binding.sosoImage.setOnClickListener {
            binding.sosoText.setTextColor(Color.CYAN)
            toast.show()
        }

        binding.nervousImage.setOnClickListener {
            binding.nervousText.setTextColor(Color.GREEN)
            toast.show()
        }

        binding.madImage.setOnClickListener {
            binding.madText.setTextColor(Color.RED)
            toast.show()
        }
    }
}