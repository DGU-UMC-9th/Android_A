package com.example.imreallystupid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActivityMainBinding
import com.google.gson.Gson


private lateinit var binding: ActivityMainBinding

private var albumData = ArrayList<Album>()
class MainActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var song: Song = Song()
    private var gson: Gson = Gson()

    fun updateMiniPlayer(album: Album) {
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Imreallystupid)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragmentContainer, HomeFragment(), null)
            .commit()

        binding.mainBottomnav.setOnItemSelectedListener { item ->
            when(item.itemId) {

                R.id.main_bottomnav_home_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                /*R.id.main_bottomnav_search_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.main_bottomnav_look_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }*/

                R.id.main_bottomnav_locker_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val sendTime = data?.getIntExtra("sendTime", 0)?.toFloat() ?: 0
                val playTime = data?.getIntExtra("playTime", 0) ?: 0
                val progress = if (playTime != 0) ((sendTime.toFloat() / playTime) * 10000).toInt() else 0
                binding.mainMiniplayerSeekbarSb.progress = progress
            }
        }



        binding.mainMiniplayer.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 60,false, "music_lilac")

            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("music",song.music)
            resultLauncher.launch(intent)
        }

    }
    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerSeekbarSb.progress = (song.second*10000)/song.playTime
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song",MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData",null)

        song = if(songJson == null){
            Song("라일락","아이유(IU)",0,60,false,"music_lilac")
        } else {
            gson.fromJson(songJson, Song::class.java)
        }
        setMiniPlayer(song)
    }
}