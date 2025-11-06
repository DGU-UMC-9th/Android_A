package com.example.week4

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.week4.databinding.ActivitySongBinding
import java.util.concurrent.TimeUnit

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding

    private var mediaPlayer: MediaPlayer? = null

    private val handler = Handler(Looper.getMainLooper())
    private var song: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        song = MusicPlayerSingleton.currentSong
        mediaPlayer = MusicPlayerSingleton.mediaPlayer

        song?.let {
            binding.songMusicTitleTv.text = it.title
            binding.songSingerNameTv.text = it.singer
            it.albumArt?.let { resId -> binding.songAlbumIv.setImageResource(resId) }

            binding.songEndTimeTv.text = millisecondsToTimer(it.playTime.toLong())
            binding.songSeekBar.max = it.playTime
            binding.songSeekBar.progress = it.second
            binding.songStartTimeTv.text = millisecondsToTimer(it.second.toLong())
        }

        binding.songMiniplayerIv.setOnClickListener {
            MusicPlayerSingleton.resume()
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            MusicPlayerSingleton.pause()
            setPlayerStatus(false)
        }

        binding.songPreviousIv.setOnClickListener { seekToStart() }
        binding.songNextIv.setOnClickListener { seekToStart() }

        binding.songSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.songStartTimeTv.text = millisecondsToTimer(progress.toLong())
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler.removeCallbacks(updater)
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    MusicPlayerSingleton.seekTo(it.progress)
                    if (MusicPlayerSingleton.isPlaying) {
                        updateSeekBar()
                    }
                }
            }
        })

        mediaPlayer?.setOnCompletionListener {
            setPlayerStatus(false)
            binding.songSeekBar.progress = 0
            binding.songStartTimeTv.text = "00:00"
            handler.removeCallbacks(updater)
        }

        binding.songDownIb.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        setPlayerStatus(MusicPlayerSingleton.isPlaying)
        if (MusicPlayerSingleton.isPlaying) {
            updateSeekBar()
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updater)
    }

    override fun onStop() {
        super.onStop()
        MusicPlayerSingleton.saveSongState(this)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun seekToStart() {
        MusicPlayerSingleton.seekTo(0)
        binding.songSeekBar.progress = 0
        binding.songStartTimeTv.text = "00:00"

        if (!MusicPlayerSingleton.isPlaying) {
            MusicPlayerSingleton.resume()
            setPlayerStatus(true)
        }
    }

    private val updater = object : Runnable {
        override fun run() {
            if (MusicPlayerSingleton.isPlaying && mediaPlayer != null) {
                val currentPosition = mediaPlayer!!.currentPosition
                binding.songSeekBar.progress = currentPosition
                binding.songStartTimeTv.text = millisecondsToTimer(currentPosition.toLong())

                handler.postDelayed(this, 1000)
            } else {
                handler.removeCallbacks(this)
                setPlayerStatus(false)
            }
        }
    }

    private fun updateSeekBar() {
        handler.post(updater)
    }

    private fun millisecondsToTimer(milliseconds: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updater)
    }
}