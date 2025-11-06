package com.example.week4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import com.example.week4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val mainPlayerHandler = Handler(Looper.getMainLooper())
    private val mainPlayerUpdater = object : Runnable {
        override fun run() {
            MusicPlayerSingleton.currentSong?.let { song ->
                if (song.musicResId == 0) {
                    binding.miniplayerRootLayout.visibility = View.GONE
                    return@let
                }

                binding.miniplayerRootLayout.visibility = View.VISIBLE
                binding.homePannelPlayingNowTv.text = "${song.title}\n${song.singer}"

                if (song.playTime > 0) {
                    binding.homeMiniplayerSeekBar.max = song.playTime

                    binding.homeMiniplayerSeekBar.progress =
                        MusicPlayerSingleton.mediaPlayer?.currentPosition ?: song.second
                }

                if (MusicPlayerSingleton.isPlaying) {
                    binding.homePannelMiniplayerPlayIv.setImageResource(R.drawable.btn_miniplay_mvpause)
                } else {
                    binding.homePannelMiniplayerPlayIv.setImageResource(R.drawable.btn_miniplayer_play)
                }

            } ?: run {
                binding.miniplayerRootLayout.visibility = View.GONE
            }
            mainPlayerHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        initBottomNavigation()
        initMiniPlayerControls()
    }

    override fun onResume() {
        super.onResume()
        mainPlayerHandler.post(mainPlayerUpdater)
    }

    override fun onPause() {
        super.onPause()
        mainPlayerHandler.removeCallbacks(mainPlayerUpdater)
    }

    override fun onStop() {
        super.onStop()
        MusicPlayerSingleton.saveSongState(this)
    }

    private fun initMiniPlayerControls() {
        binding.homePannelMiniplayerPlayIv.setOnClickListener {
            if (MusicPlayerSingleton.isPlaying) {
                MusicPlayerSingleton.pause()
            } else {
                MusicPlayerSingleton.resume()
            }
        }

        binding.homeMiniplayerPreviousIv.setOnClickListener { MusicPlayerSingleton.seekTo(0) }
        binding.homeMiniplayerNextIv.setOnClickListener { MusicPlayerSingleton.seekTo(0) }

        binding.miniplayerRootLayout.setOnClickListener {
            startActivity(Intent(this, SongActivity::class.java))
        }

        binding.homeMiniplayerSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    MusicPlayerSingleton.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initBottomNavigation(){
        binding.mainBnv.setOnItemSelectedListener { item ->
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
}