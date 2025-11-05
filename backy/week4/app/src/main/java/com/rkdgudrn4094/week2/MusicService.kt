package com.rkdgudrn4094.week2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
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
            mediaPlayer = MediaPlayer.create(this, R.raw.music_lilac)
            currentSongTitle = initialTitle
            currentSongArtist = initialArtist
            if (isPlaying) {
                mediaPlayer?.start()
            }
        }

        val notification = createNotification()
        startForeground(NOTI_ID, notification)

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Music Service Channel",
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
            .setContentText("$currentSongTitle 이/가 재생 중입니다.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setOnlyAlertOnce(true)

        return notificationBuilder.build()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun playMusic() {
        mediaPlayer?.start()
    }
    //음악 멈춤
    fun pauseMusic() {
        mediaPlayer?.pause()
    }
    //인자로 받은 위치로 곡의 재생 위치 이동
    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }
    //현재 재생중인 노래 정보 업데이트
    fun updateCurrentSongInfo(title: String, artist: String) {
        currentSongTitle = title
        currentSongArtist = artist
    }
    //현재 재생중인 노래의 길이 리턴
    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }
    //현재 노래의 위치 리턴(SeekBar에 넣을 거)
    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }
    //재생 중임?
    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}