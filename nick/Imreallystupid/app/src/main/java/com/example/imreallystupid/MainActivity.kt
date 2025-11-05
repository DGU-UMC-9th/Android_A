package com.example.imreallystupid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragmentContainer, HomeFragment(), null)
            .commit()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val sendTime = data?.getIntExtra("sendTime", 0)?.toFloat() ?: 0
                val playTime = data?.getIntExtra("playTime", 0) ?: 0
                val progress = if (playTime != 0) ((sendTime.toFloat() / playTime) * 10000).toInt() else 0
                binding.mainMiniplayerSeekbarSb.progress = progress
            }
        }

<<<<<<< Updated upstream
        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())
=======

>>>>>>> Stashed changes

        binding.mainMiniplayer.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 60,false)

            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            resultLauncher.launch(intent)
        }

    }
}