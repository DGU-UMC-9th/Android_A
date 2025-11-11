package com.example.mission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mission2.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(),HomeFragment.OnSongPlayListener {

    lateinit var binding: ActivityMainBinding
    private var song:Song = Song()
    private var gson:Gson= Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("music",song.music)
            startActivity(intent)

        }

    }
    override fun onSongPlayed(title: String?, singer: String?){
        binding.mainMiniplayerTitleTv.text = title
        binding.mainMiniplayerSingerTv.text = singer
        binding.mainMiniplayerTitleTv.text = title
        binding.mainMiniplayerSingerTv.text = singer
    }



    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text=song.title
        binding.mainMiniplayerSingerTv.text=song.singer
        binding.mainMiniplayerProgressSb.progress=(song.second*100000)/song.playTime
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences=getSharedPreferences("song",MODE_PRIVATE)
        val songJson=sharedPreferences.getString("songData",null)

        song=if(songJson==null) {
            Song("라일락", "아이유(IU)", 0, 60, false, "unvailable")
        }else{
            gson.fromJson(songJson,Song::class.java)

        }
        setMiniPlayer(song)
    }

}