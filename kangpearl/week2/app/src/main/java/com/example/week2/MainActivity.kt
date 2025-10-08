package com.example.week2

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week2.databinding.ActivityMainBinding
import com.example.week2.databinding.FragmentHomeBinding

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

    //val textView=findViewById<TextView 거나 ImageView>(R.id.album_album_iv)
    //findViewById: xml에 있는 View를 가져올 수 있음
    //R: Resource
    //R.id.album_album_iv -> album_album_iv가 id인 ImageView를 가져옴