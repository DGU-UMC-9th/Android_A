package com.example.mission2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mission2.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    //week5. song data 초기화 함수 생성
    lateinit var song : Song
    lateinit var timer:Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // week5. Song 데이터 초기화 및 UI 설정
        initSong()

        setPlayer(song)

        // 뒤로가기 버튼
        binding.songDownIb.setOnClickListener {
            finish()
        }

        // 재생/일시정지 버튼
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
    }
    override fun onDestroy(){
        super.onDestroy() // 쓰레드 종료
        timer.interrupt()
    }
//
//        // 반복재생 버튼
//        binding.songRepeatIv.setOnClickListener {
//            setRepeatStatus(true)
//        }
//        binding.songSelectRepeatIv.setOnClickListener {
//            setRepeatStatus(false)
//        }
//
//        // 랜덤재생 버튼
//        binding.songRandomIv.setOnClickListener {
//            setRandomStatus(true)
//        }
//        binding.songSelectRandomIv.setOnClickListener {
//            setRandomStatus(false)
//        }

    // week5. Song 객체를 초기화하는 함수
    private fun initSong() {
        // Intent로 넘어온 데이터가 있는지 확인
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 60), // playTime이 0이면 안되므로 기본값 60초 설정
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    //week5. UI에 노래 정보를 반영하는 함수
    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60) // song.second -> song.playTime 수정
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying=isPlaying
        timer.isPlaying=isPlaying

        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun startTimer(){
        timer=Timer(song.playTime,song.isPlaying)
        timer.start()
        }
// 바인딩 변수를 이용해야해서 inner class 로 생성
    inner class Timer(private val playTime:Int, var isPlaying: Boolean=true):Thread(){
        private var second : Int=0
        private var mills:Float=0f

    override fun run(){
        super.run()
        try{


        while(true){

            if(second>= playTime){
                break
            }

            if(isPlaying){
                sleep(50)
                mills+=50

                runOnUiThread {
                    binding.songProgressSb.progress=((mills/playTime)*100).toInt()
                    // seekbar 구현
                }
                if(mills%1000==0f){
                    runOnUiThread {
                        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
                    }
                    song.second++
                }
            }
        }
        }catch(e: InterruptedException){
            Log.d("Song","쓰레드가 종료되었습니다.")
        }
    }
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