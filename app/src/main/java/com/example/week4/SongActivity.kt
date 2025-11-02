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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.butter)

        binding.songEndTimeTv.text = millisecondsToTimer(mediaPlayer?.duration?.toLong() ?: 0)
        binding.songSeekBar.max = mediaPlayer?.duration ?: 0

        binding.songMiniplayerIv.setOnClickListener {
            mediaPlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                    updateSeekBar()
                    binding.songMiniplayerIv.visibility = View.GONE
                    binding.songPauseIv.visibility = View.VISIBLE
                }
            }
        }

        binding.songPauseIv.setOnClickListener {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    handler.removeCallbacks(updater)
                    binding.songMiniplayerIv.visibility = View.VISIBLE
                    binding.songPauseIv.visibility = View.GONE
                }
            }
        }

        binding.songPreviousIv.setOnClickListener { seekToStart() }
        binding.songNextIv.setOnClickListener { seekToStart() }

        binding.songSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler.removeCallbacks(updater)
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (mediaPlayer?.isPlaying == true) {
                    updateSeekBar()
                }
            }
        })

        mediaPlayer?.setOnCompletionListener {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            binding.songSeekBar.progress = 0
            binding.songStartTimeTv.text = "00:00"
            handler.removeCallbacks(updater)
        }

        binding.songDownIb.setOnClickListener {
            finish()
        }
    }

    private fun seekToStart() {
        mediaPlayer?.seekTo(0)
        if (mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
            updateSeekBar()
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    private val updater = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    binding.songSeekBar.progress = it.currentPosition
                    binding.songStartTimeTv.text = millisecondsToTimer(it.currentPosition.toLong())

                    handler.postDelayed(this, 1000)
                }
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
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacks(updater)
    }
}