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
<<<<<<< Updated upstream
=======

    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer

    var sendTime : Int = 0

>>>>>>> Stashed changes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resend = Intent(this, MainActivity::class.java)

        binding.songDownIv.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("sendTime", sendTime)
                putExtra("playTime", song.playTime)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        binding.songPlayIv.setOnClickListener {
            setPlayerStatus(false)
        }
<<<<<<< Updated upstream
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
=======

        setPlayer(song)

    }


    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong() {
        if(intent.hasExtra("title") && intent.hasExtra("singer"))  {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
>>>>>>> Stashed changes
        }

        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songTitleTv.text = intent.getStringExtra("title")
            binding.songSingerTv.text = intent.getStringExtra("singer")
        }


    }

<<<<<<< Updated upstream
}
=======
    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true): Thread(){

        private var second : Int = 0

        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while(true) {
                    if(second >= playTime) {
                        break
                    }

                    if(isPlaying){
                        sleep(50)
                        mills += 50
                        sendTime = second

                        runOnUiThread {
                            binding.songSeekbarSb.progress = ((mills/playTime)*10).toInt()
                        }
                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException) {
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }

}

>>>>>>> Stashed changes
