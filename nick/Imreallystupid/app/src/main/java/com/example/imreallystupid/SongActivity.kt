package com.example.imreallystupid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActivitySongBinding

private lateinit var binding: ActivitySongBinding


class SongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}