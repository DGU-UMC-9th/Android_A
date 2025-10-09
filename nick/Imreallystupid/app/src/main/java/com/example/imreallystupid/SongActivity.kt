package com.example.imreallystupid

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActivitySongBinding

private lateinit var binding: ActivitySongBinding

fun setPlayerStatus(isPlaying : Boolean) {
    if(isPlaying) {
        binding.songPlayIv.visibility = View.VISIBLE
        binding.songPauseIv.visibility = View.GONE
    }
    else {
        binding.songPlayIv.visibility = View.GONE
        binding.songPauseIv.visibility = View.VISIBLE
    }
}
class SongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIv.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("reply", binding.songTitleTv.text.toString())
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        binding.songPlayIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songTitleTv.text = intent.getStringExtra("title")
            binding.songSingerTv.text = intent.getStringExtra("singer")
        }


    }

}