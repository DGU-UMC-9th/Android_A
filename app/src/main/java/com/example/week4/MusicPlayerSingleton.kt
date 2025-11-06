package com.example.week4

import android.content.Context
import android.media.MediaPlayer
import com.google.gson.Gson

object MusicPlayerSingleton {
    var mediaPlayer: MediaPlayer? = null
    var currentSong: Song? = null
    private val gson: Gson = Gson()
    private const val PREF_NAME = "song"
    private const val PREF_KEY_SONG = "songData"

    val isPlaying: Boolean
        get() = currentSong?.isPlaying ?: false

    fun playSong(context: Context, song: Song) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, song.musicResId)

        mediaPlayer?.setOnPreparedListener {
            song.playTime = it.duration
        }

        mediaPlayer?.start()

        song.isPlaying = true
        currentSong = song
        saveSongState(context)
    }

    fun pause() {
        mediaPlayer?.pause()
        currentSong?.isPlaying = false
    }

    fun resume() {
        mediaPlayer?.start()
        currentSong?.isPlaying = true
    }

    fun seekTo(progress: Int) {
        mediaPlayer?.seekTo(progress)
        currentSong?.second = progress
    }

    fun saveSongState(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        mediaPlayer?.let {
            currentSong?.second = it.currentPosition
            currentSong?.playTime = it.duration
            currentSong?.isPlaying = it.isPlaying
        }

        val songJson = gson.toJson(currentSong)

        editor.putString(PREF_KEY_SONG, songJson)
        editor.apply()
    }

    fun loadSongState(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val songJson = sharedPreferences.getString(PREF_KEY_SONG, null)

        if (songJson != null) {
            try {
                val song = gson.fromJson(songJson, Song::class.java)
                if (song.musicResId != 0) {
                    currentSong = song

                    mediaPlayer = MediaPlayer.create(context, song.musicResId)
                    mediaPlayer?.seekTo(song.second)

                    mediaPlayer?.setOnPreparedListener {
                        song.playTime = it.duration
                    }

                    if (song.isPlaying) {
                        mediaPlayer?.start()
                    }
                }
            } catch (e: Exception) {
                currentSong = null
            }
        }
    }
}