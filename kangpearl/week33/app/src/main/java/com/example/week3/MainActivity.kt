package com.example.week3

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week3.databinding.ActivityMainBinding
import com.example.week3.databinding.FragmentHomeBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.miniplayerRootLayout.setOnClickListener {
            startActivity(Intent(this, SongActivity::class.java))
        }
    }
}