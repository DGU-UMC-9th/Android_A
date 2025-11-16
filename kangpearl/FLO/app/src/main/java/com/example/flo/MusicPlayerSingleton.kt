package com.example.flo
//테스트//테스트
import android.content.Context
import android.media.MediaPlayer
import com.google.gson.Gson
import android.util.Log

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

        val musicResId = context.resources.getIdentifier(song.music, "raw", context.packageName)
        if (musicResId == 0) {
            return
        }

        mediaPlayer = MediaPlayer.create(context, musicResId)

        song.playTime = mediaPlayer?.duration ?: 0

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
        mediaPlayer?.let { player ->
            try {
                player.seekTo(progress)
                currentSong?.second = progress
            } catch (e: Exception) {
                Log.e("MusicPlayerSingleton", "seekTo failed: ${e.message}")
            }
        }
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
                val musicResId = context.resources.getIdentifier(song.music, "raw", context.packageName)

                if (musicResId != 0) {
                    currentSong = song
                    mediaPlayer = MediaPlayer.create(context, musicResId)
                    mediaPlayer?.seekTo(song.second)

                    currentSong?.playTime = mediaPlayer?.duration ?: 0
                }
            } catch (e: Exception) {
                currentSong = null
            }
        }
    }
}