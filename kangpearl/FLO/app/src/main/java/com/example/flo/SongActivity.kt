package com.example.flo
//테스트//테스트
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var songDB: SongDatabase

    var songs = ArrayList<Song>()
    var nowPos = 0

    private val songActivityHandler = Handler(Looper.getMainLooper())
    private val songActivityUpdater = object : Runnable {
        override fun run() {
            val player = MusicPlayerSingleton.mediaPlayer
            val currentSong = MusicPlayerSingleton.currentSong

            if (player != null && currentSong != null) {
                try {
                    binding.songSeekBar.max = player.duration
                    binding.songSeekBar.progress = player.currentPosition
                    binding.songStartTimeTv.text =
                        String.format("%02d:%02d", player.currentPosition / 60000, (player.currentPosition / 1000) % 60)
                    binding.songEndTimeTv.text =
                        String.format("%02d:%02d", player.duration / 60000, (player.duration / 1000) % 60)

                    if (MusicPlayerSingleton.isPlaying) {
                        binding.songMiniplayerIv.visibility = View.GONE
                        binding.songPauseIv.visibility = View.VISIBLE
                    } else {
                        binding.songMiniplayerIv.visibility = View.VISIBLE
                        binding.songPauseIv.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    Log.e("SongActivity", "songActivityUpdater 오류 발생", e)
                }
            }
            songActivityHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songDB = SongDatabase.getInstance(this)!!

        initPlayList()
        initSong()
        initClickListener()
    }

    override fun onResume() {
        super.onResume()
        songActivityHandler.post(songActivityUpdater)
    }

    override fun onPause() {
        super.onPause()
        songActivityHandler.removeCallbacks(songActivityUpdater)

        MusicPlayerSingleton.saveSongState(this)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songs[nowPos].id)
        editor.apply()
    }

    private fun initPlayList() {
        songs = songDB.songDao().getSongs() as ArrayList<Song>
    }

    private fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        if (MusicPlayerSingleton.currentSong?.id != songs[nowPos].id) {
            MusicPlayerSingleton.playSong(this, songs[nowPos])
        }

        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) {
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songAlbumIv.setImageResource(song.coverImg!!)

        MusicPlayerSingleton.mediaPlayer?.let {
            try {
                binding.songEndTimeTv.text =
                    String.format("%02d:%02d", it.duration / 60000, (it.duration / 1000) % 60)
                binding.songSeekBar.max = it.duration
            } catch (e: Exception) {
                Log.e("SongActivity", "setPlayer duration/max 설정 오류", e)
            }
        }

        setLikeStatus(song.isLike)

        if (MusicPlayerSingleton.isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun setLikeStatus(isLike: Boolean) {
        if (isLike) {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun initClickListener() {
        binding.songDownIb.setOnClickListener { finish() }

        binding.songMiniplayerIv.setOnClickListener {
            MusicPlayerSingleton.resume()
        }
        binding.songPauseIv.setOnClickListener {
            MusicPlayerSingleton.pause()
        }

        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos])
        }

        binding.songSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    MusicPlayerSingleton.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        MusicPlayerSingleton.playSong(this, songs[nowPos])

        setPlayer(songs[nowPos])
    }

    private fun setLike(song: Song) {
        song.isLike = !song.isLike
        songDB.songDao().update(song)
        setLikeStatus(song.isLike)

        if (song.isLike) {
            Toast.makeText(this, "보관함에 담았습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "보관함에서 삭제했습니다.", Toast.LENGTH_SHORT).show()
        }

        if (song.id == MusicPlayerSingleton.currentSong?.id) {
            MusicPlayerSingleton.currentSong?.isLike = song.isLike
        }
    }
}