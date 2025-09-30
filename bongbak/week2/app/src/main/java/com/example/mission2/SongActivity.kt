package com.example.mission2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mission2.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }
        if (intent.hasExtra("title")&&intent.hasExtra("singer")){
            binding.songMusicTitleTv.text=intent.getStringExtra("title")
            binding.songSingerNameTv.text=intent.getStringExtra("singer")
        }

    }
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){

        }
        else{
            binding.songMiniplayerIv.visibility= View.GONE
            binding.songPauseIv.visibility=View.VISIBLE
        }
    }



}