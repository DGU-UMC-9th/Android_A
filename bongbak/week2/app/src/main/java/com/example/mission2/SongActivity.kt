package com.example.mission2

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mission2.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    //week5. song data 초기화 함수 생성
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson=Gson()

    val songs=arrayListOf<Song>()
    lateinit var songDB:SongDatabase
    var nowPos=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // week5. Song 데이터 초기화 및 UI 설정

        initPlayList()
        initSong()
        initClickListener()

    }

    // week5. Song 객체를 초기화하는 함수

    private fun initClickListener(){
        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songLikeIv.setOnClickListener{
            setLike(songs[nowPos].isLike)
        }
    }
    private fun initSong() {
       val spf=getSharedPreferences("song",MODE_PRIVATE)
        val songId=spf.getInt("songId",0)

        nowPos=getPlayingSongPosition(songId)

        Log.d("now Song Id", songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike!=isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }
        else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun moveSong(direct: Int){
        if(nowPos*direct<0){
            Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos*direct>=songs.size){
            Toast.makeText(this,"Last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos+=direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer=null

        setPlayer(songs[nowPos])

    }

    private fun getPlayingSongPosition(songId:Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id==songId){
                return i
            }
        }
        return 0
    }

    //week5. UI에 노래 정보를 반영하는 함수
    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text =
            String.format("%02d:%02d", song.second / 60, song.second % 60)

        binding.songEndTimeTv.text = String.format(
            "%02d:%02d",
            song.playTime / 60,
            song.playTime % 60
        )
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second*100*1000/song.playTime)
        Log.d("song","second=${song.second}")

        val music=resources.getIdentifier(song.music,"raw",this.packageName)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }
        else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) { // 음악이 재생 중일때만 pause를 해야함.
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    // 바인딩 변수를 이용해야해서 inner class 로 생성
    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
        private var second: Int = 0
        private var mills: Float = songs[nowPos].second*1000F

        override fun run() {
            super.run()
            try {


                while (true) {

                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                            // seekbar 구현
                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTimeTv.text =
                                    String.format("%02d:%02d", songs[nowPos].second / 60, songs[nowPos].second % 60)
                            }
                            songs[nowPos].second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 종료되었습니다.")
            }
        }
    }


    // 사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second=((binding.songProgressSb.progress*songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터

        editor.putInt("songId",songs[nowPos].id)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy() // 쓰레드 종료
        timer.interrupt()
        mediaPlayer?.release() // 미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer=null //미디어 플레이어 해제
    }

    private fun initPlayList(){
        songDB= SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }


    /*
        private fun setRepeatStatus(isRepeat: Boolean) {
            if (isRepeat) {
                binding.songRepeatIv.visibility = View.GONE
                binding.songSelectRepeatIv.visibility = View.VISIBLE
            } else {
                binding.songRepeatIv.visibility = View.VISIBLE
                binding.songSelectRepeatIv.visibility = View.GONE
            }
        }

        private fun setRandomStatus(isRandom: Boolean) {
            if (isRandom) {
                binding.songRandomIv.visibility = View.GONE
                binding.songSelectRandomIv.visibility = View.VISIBLE
            } else {
                binding.songRandomIv.visibility = View.VISIBLE
                binding.songSelectRandomIv.visibility = View.GONE
            }
        }
        */
}