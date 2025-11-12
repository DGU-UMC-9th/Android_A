package com.rkdgudrn4094.week2

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.rkdgudrn4094.week2.databinding.ActivitySongBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer

    /*
    private var musicService: MusicService?= null
    private var isBound = false
    private var updateJob: Job?= null

     */
    private var mediaPlayer: MediaPlayer?=null
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) // root: xml 최상단 뷰

        initSong()
        setPlayer(song)
        //Log.d("SongActivity", "from onCreate()")

        /*
        val musicServiceIntent = Intent(this, MusicService::class.java).apply{
            putExtra("songTitle", song?.title?:"Unknown")
            putExtra("songArtist", song?.singer?:"Unknown")
            putExtra("isPlaying", true)
        }
        ContextCompat.startForegroundService(this, musicServiceIntent)
        bindService(musicServiceIntent, connection, Context.BIND_AUTO_CREATE)

         */


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
            //musicService?.playMusic()
            setPlayerStatus(true)
            Log.d("Activity", "SongActivity, song.second:${song.second}")
        }
        binding.songPauseIv.setOnClickListener {
            //musicService?.pauseMusic()
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
            timer = Timer(song.playTime, song.second, song.isPlaying)
            timer.start()
            mediaPlayer?.seekTo(0)
        }
        binding.songPreviousIv.setOnClickListener {
            timer.resetTimer()
            timer.interrupt()
            timer = Timer(song.playTime, song.second, song.isPlaying)
            timer.start()
            mediaPlayer?.seekTo(0)
        }

        /*
        binding.songProgressSb.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    musicService?.seekTo(progress)
                    binding.songStartTimeTv.text
                }
            }
        })

         */
    }

    fun setPlayerStatus(isPlaying: Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.second, song.isPlaying)
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
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text=intent.getStringExtra("title")
        binding.songSingerNameTv.text=intent.getStringExtra("singer")
        binding.songStartTimeTv.text=String.format("%02d:%02d", song.second/60, song.second%60)
        binding.songEndTimeTv.text=String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        binding.songProgressSb.progress = (song.second *100 * 1000 / song.playTime)
        Log.d("song", "second=${song.second}")

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        /*
        mediaPlayer?.seekTo(song.second * 1000)*/

        setPlayerStatus(song.isPlaying)

        /*
        timer = Timer(song.playTime, song.second, song.isPlaying)
        timer.start()*/
    }

    inner class Timer(private val playTime: Int, var songSecond: Int, var isPlaying: Boolean = true): Thread(){
        private var second: Int = songSecond
        private var mills: Float = (second*1000).toFloat()

        override fun run() {
            super.run()
            try{
                while (true){
                    /*
                    val currentMills = mediaPlayer!!.currentPosition
                    val currentSecond: Int = (currentMills / 1000)

                     */

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
                            song.second++
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

        fun getMills(): Float{
            return mills
            //return ((mills/playTime)*100).toInt()
        }
    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        //song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        Log.d("Activity", "SongActivity, song.second:${song.second}")
        editor.putString("songData", songJson)

        editor.apply()
    }

    /*
    private val connection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService=binder.getService()
            isBound=true

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound=false
        }
    }

    private fun updateSeekbar(timer: Timer){
        updateJob?.cancel()
        updateJob = lifecycleScope.launch(Dispatchers.Main){
            while (isBound && musicService?.isPlaying() == true){
                delay(100)
                val currentPosition = musicService!!.getCurrentPosition()
            }
        }
    }

     */

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        song = if (songJson == null){
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        } else {
            gson.fromJson(songJson, song::class.java)
        }
        //song.second = 0

        setPlayer(song)
        mediaPlayer?.seekTo(song.second*1000)
        //Log.d("SongActivity", "from onStart()")
    }
}