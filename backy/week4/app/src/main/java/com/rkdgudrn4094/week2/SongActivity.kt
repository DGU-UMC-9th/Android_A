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
import android.widget.Toast
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
    //lateinit var song: Song
    lateinit var timer: Timer


    private var musicService: MusicService?= null
    private var isBound = false
    private var updateJob: Job?= null

    private val connection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBound = true
            //updateSeekbar()
            startTimer()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }


    //private var mediaPlayer: MediaPlayer?=null
    private var gson: Gson = Gson()
    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root) // root: xml 최상단 뷰

        initPlayList()
        initSong()
        initClickListener()

        val musicServiceIntent = Intent(this, MusicService::class.java).apply{
            putExtra("songId", songs[nowPos].id)
            putExtra("songTitle", songs[nowPos].title?:"Unknown")
            putExtra("songArtist", songs[nowPos].singer?:"Unknown")
            putExtra("isPlaying", true)
        }
        ContextCompat.startForegroundService(this, musicServiceIntent)
        bindService(musicServiceIntent, connection, Context.BIND_AUTO_CREATE)
        //setPlayer(song)
    }

    /*
    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        songs[nowPos] = if (songJson == null){
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        } else {
            gson.fromJson(songJson, songs[nowPos]::class.java)
        }
        //song.second = 0

        setPlayer(songs[nowPos])
        mediaPlayer?.seekTo(songs[nowPos].second*1000)
        //Log.d("SongActivity", "from onStart()")
    }

     */

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        musicService?.release()
        //mediaPlayer?.release()
        //mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()

        //setPlayerStatus(false)
        //songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        val second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000

        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //val songJson = gson.toJson(songs[nowPos])
        //Log.d("Activity", "SongActivity, song.second:${song.second}")
        editor.putInt("songId", songs[nowPos].id)

        songDB.songDao().updateSecond(second, songs[nowPos].id)

        editor.apply()
    }

    private fun initSong(){
        /*
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }*/

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        Log.d("now Song ID", songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    fun setPlayerStatus(isPlaying: Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            //mediaPlayer?.start()
            musicService?.playMusic()
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            /*
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }

             */

            if (musicService?.isPlaying() == true){
                musicService?.pauseMusic()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime, songs[nowPos].second, songs[nowPos].isPlaying)
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





    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text=song.title
        binding.songSingerNameTv.text=song.singer
        binding.songStartTimeTv.text=String.format("%02d:%02d", song.second/60, song.second%60)
        binding.songEndTimeTv.text=String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second *100 * 1000 / song.playTime)
        Log.d("song", "second=${song.second}")

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        musicService?.createMediaPlayer(music)
        //mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)

        /*
        timer = Timer(song.playTime, song.second, song.isPlaying)
        timer.start()*/
    }

    private fun moveSong(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos+=direct

        timer.interrupt()
        startTimer()

        /*
        mediaPlayer?.release()
        mediaPlayer=null
        */
        musicService?.release()

        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
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
                        Log.d("times", "mills: ${mills}, second: ${second}")

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text=String.format("%02d:%02d", second/60, second%60)
                            }
                            //second++
                            //songs[nowPos].second++
                            second = (mills / 1000).toInt()
                            songs[nowPos].second = second
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

        fun updateMills(newMills: Float){
            mills = newMills
            second = (mills / 1000).toInt()
            binding.songStartTimeTv.text=String.format("%02d:%02d", second/60, second%60)
        }
    }




    private fun updateSeekbar(){
        updateJob?.cancel()
        updateJob = lifecycleScope.launch(Dispatchers.Main){
            while (isBound && musicService?.isPlaying() == true){
                delay(100)
                val currentPosition = musicService!!.getCurrentPosition()
                binding.songProgressSb.progress = currentPosition
            }
        }
    }

    private fun initClickListener(){
        binding.songDownIb.setOnClickListener {
            /*
            val song = Song(binding.songMusicTitleTv.text.toString(), binding.songSingerNameTv.text.toString())
            val intent = Intent(this, MainActivity::class.java).apply{
                putExtra("title", song.title)
                putExtra("singer", song.singer)
            }
            setResult(Activity.RESULT_OK, intent)*/
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            musicService?.playMusic()
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            musicService?.pauseMusic()
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
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)

            var tmp:Int = 0
            for (i in songs){
                Log.d("isLike", "${++tmp}: ${i.isLike}")
            }
        }


        binding.songProgressSb.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    musicService?.seekTo(progress * songs[nowPos].playTime / 100)
                    Log.d("progress", (progress * songs[nowPos].playTime / 100f).toString())

                    //binding.songStartTimeTv.text = ((progress / 1000f) * songs[nowPos].playTime).toString()
                    timer.updateMills(adjustMills(progress * songs[nowPos].playTime / 100))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


    }

    private fun adjustMills(input: Int): Float{
        val a = input/50
        val b = input%50

        if (b<25){
            return a * 50f
        } else {
            return (a + 1) * 50f
        }
    }
}