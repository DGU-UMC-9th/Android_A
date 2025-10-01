package com.rkdgudrn4094.week2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rkdgudrn4094.week2.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) // root: xml 최상단 뷰

        binding.songDownIb.setOnClickListener {
            val song = Song(binding.songMusicTitleTv.text.toString(), binding.songSingerNameTv.text.toString())
            val intent = Intent(this, MainActivity::class.java).apply{
                putExtra("title", song.title)
                putExtra("singer", song.singer)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songRepeatIv.setOnClickListener {
            setVisibility(binding.songRepeatActiveIv, binding.songRepeatIv)
        }
        binding.songRepeatActiveIv.setOnClickListener {
            setVisibility(binding.songRepeatIv, binding.songRepeatActiveIv)
        }

        binding.songRandomIv.setOnClickListener {
            setVisibility(binding.songRandomActiveIv, binding.songRandomIv)
        }
        binding.songRandomActiveIv.setOnClickListener {
            setVisibility(binding.songRandomIv, binding.songRandomActiveIv)
        }


        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text=intent.getStringExtra("title")
            binding.songSingerNameTv.text=intent.getStringExtra("singer")
        }
    }

    fun setPlayerStatus(isPlaying: Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    fun setVisibility(/*isPlaying: Boolean, */iv_Visible: android.widget.ImageView, iv_Gone: android.widget.ImageView){
        iv_Visible.visibility=View.VISIBLE
        iv_Gone.visibility=View.GONE

        /*if (isPlaying){
            iv1.visibility=View.VISIBLE
            iv2.visibility=View.GONE
        }

        else{
            iv1.visibility=View.GONE
            iv2.visibility=View.VISIBLE
        }*/
    }
}