package com.rkdgudrn4094.week2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rkdgudrn4094.week2.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) // root: xml 최상단 뷰

        initSong()
        setPlayer(song)

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
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
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

        /*
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text=intent.getStringExtra("title")
            binding.songSingerNameTv.text=intent.getStringExtra("singer")
        }

         */

        binding.songNextIv.setOnClickListener {
            timer.resetTimer()
            timer.interrupt()
            timer = Timer(song.playTime, song.isPlaying)
            timer.start()
        }
        binding.songPreviousIv.setOnClickListener {
            timer.resetTimer()
            timer.interrupt()
            timer = Timer(song.playTime, song.isPlaying)
            timer.start()
        }
    }

    fun setPlayerStatus(isPlaying: Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
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

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text=intent.getStringExtra("title")
        binding.songSingerNameTv.text=intent.getStringExtra("singer")
        binding.songStartTimeTv.text=String.format("%02d:%02d", song.second/60, song.second%60)
        binding.songEndTimeTv.text=String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true): Thread(){
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try{
                while (true){
                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills +=50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text=String.format("%02d:%02d", second/60, second%60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song", "thread dead ${e.message}")
            }

        }

        fun resetTimer(){
            second = 0
            mills = 0f

            binding.songProgressSb.progress=0
            binding.songStartTimeTv.text="00:00"
        }
    }
}