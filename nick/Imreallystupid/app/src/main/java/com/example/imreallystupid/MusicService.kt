/*package com.example.imreallystupid

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MusicService : Service() {

    private val CHANNEL_ID = "ForegroundMusicService"
    private val NOTI_ID = 713

    private var mediaPlayer: MediaPlayer? = null
    private val binder = MusicBinder()

    private var currentSongTitle: String = "Unknown Title"
    private var currentSongArtist: String = "Unknown Artist"

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val initialTitle = intent?.getStringExtra("songTitle") ?: "Unknown Title"
        val initialArtist = intent?.getStringExtra("songArtist") ?: "Unknown Artist"
        val isPlaying = intent?.getBooleanExtra("isPlaying", false) ?: false

        if (mediaPlayer == null) {
            mediaPlayer == MediaPlayer.create(this, R.)
            currentSongTitle = initialTitle
            currentSongArtist = initialArtist
            if(isPlaying) {
                mediaPlayer?.start()
            }
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Music Service CHannel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("음악 재생 중")
            .setContentText("$currentSongTitle 이/가 재생중입니다.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setOnlyAlertOnce(true)

        return notificationBuilder.build()
    }

    fun playMusic() {
        mediaPlayer?.start()
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun updateCurrentSongInfo(title: String, artist: String) {
        currentSongArtist = artist
        currentSongTitle = title
    }

    fun getCurrentPosition() : Int {
        return mediaPlayer?.duration ?: 0
    }

    fun isPlaying() : Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}*/