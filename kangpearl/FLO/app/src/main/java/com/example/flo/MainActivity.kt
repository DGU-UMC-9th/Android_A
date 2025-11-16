package com.example.flo
//테스트//테스트
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var songDB: SongDatabase? = null
    private var songs = ArrayList<Song>()

    private val mainPlayerHandler = Handler(Looper.getMainLooper())
    private val mainPlayerUpdater = object : Runnable {
        override fun run() {
            val player = MusicPlayerSingleton.mediaPlayer
            val currentSong = MusicPlayerSingleton.currentSong

            if (currentSong != null && player != null) {
                try {
                    binding.miniplayerRootLayout.visibility = View.VISIBLE
                    binding.homePannelPlayingNowTv.text = "${currentSong.title}\n${currentSong.singer}"

                    val duration = player.duration
                    if (duration > 0) {
                        binding.homeMiniplayerSeekBar.max = duration
                        binding.homeMiniplayerSeekBar.progress = player.currentPosition
                    }

                    if (MusicPlayerSingleton.isPlaying) {
                        binding.homePannelMiniplayerPlayIv.setImageResource(R.drawable.btn_miniplay_mvpause)
                    } else {
                        binding.homePannelMiniplayerPlayIv.setImageResource(R.drawable.btn_miniplayer_play)
                    }

                } catch (e: Exception) {
                    Log.e("MainActivity", "mainPlayerUpdater 오류 발생", e)
                }
            } else {
                binding.miniplayerRootLayout.visibility = View.GONE
            }
            mainPlayerHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songDB = SongDatabase.getInstance(this)
        songs.addAll(songDB!!.songDao().getSongs())

        inputDummySongs()

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

        binding.homeMiniplayerPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.homeMiniplayerNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.miniplayerRootLayout.setOnClickListener {
            MusicPlayerSingleton.currentSong?.let { song ->
                val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("songId", song.id)
                editor.apply()
            }
            startActivity(Intent(this, SongActivity::class.java))
        }

        binding.homeMiniplayerSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(SeekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    MusicPlayerSingleton.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(SeekBar: SeekBar?) {}
            override fun onStopTrackingTouch(SeekBar: SeekBar?) {}
        })
    }

    private fun moveSong(direct: Int) {
        val currentSongId = MusicPlayerSingleton.currentSong?.id ?: return
        var nowPos = songs.indexOfFirst { it.id == currentSongId }

        if (nowPos == -1) return

        nowPos += direct

        if (nowPos < 0) {
            nowPos = 0
        } else if (nowPos >= songs.size) {
            nowPos = songs.size - 1
        }

        MusicPlayerSingleton.playSong(this, songs[nowPos])
        updateMiniPlayer()
    }

    private fun initBottomNavigation() {
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

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                title = "IU 5th Album 'LILAC'",
                singer = "아이유 (IU)",
                coverImg = R.drawable.img_album_exp2
            )
        )
        songDB.albumDao().insert(
            Album(
                title = "Butter",
                singer = "방탄소년단 (BTS)",
                coverImg = R.drawable.img_album_exp
            )
        )
        songDB.albumDao().insert(
            Album(
                title = "iScreaM Vol.10 : Next Level Remixes",
                singer = "에스파 (AESPA)",
                coverImg = R.drawable.img_first_album_default
            )
        )

        val songDao = songDB.songDao()

        songDao.insert(
            Song(
                title = "Lilac",
                singer = "아이유 (IU)",
                second = 0,
                playTime = 200000,
                isPlaying = false,
                music = "lilac",
                coverImg = R.drawable.img_album_exp2,
                isLike = false,
                albumIdx = 1
            )
        )
        songDao.insert(
            Song(
                title = "Flu",
                singer = "아이유 (IU)",
                second = 0,
                playTime = 200000,
                isPlaying = false,
                music = "flu",
                coverImg = R.drawable.img_album_exp2,
                isLike = false,
                albumIdx = 1
            )
        )
        songDao.insert(
            Song(
                title = "Coin",
                singer = "아이유 (IU)",
                second = 0,
                playTime = 200000,
                isPlaying = false,
                music = "coin",
                coverImg = R.drawable.img_album_exp2,
                isLike = false,
                albumIdx = 1
            )
        )
        songDao.insert(
            Song(
                title = "Butter",
                singer = "방탄소년단 (BTS)",
                second = 0,
                playTime = 190000,
                isPlaying = false,
                music = "butter",
                coverImg = R.drawable.img_album_exp,
                isLike = false,
                albumIdx = 2
            )
        )
        songDao.insert(
            Song(
                title = "Permission to Dance",
                singer = "방탄소년단 (BTS)",
                second = 0,
                playTime = 200000,
                isPlaying = false,
                music = "permission",
                coverImg = R.drawable.img_album_exp,
                isLike = false,
                albumIdx = 2
            )
        )
        songDao.insert(
            Song(
                title = "Next Level",
                singer = "에스파 (AESPA)",
                second = 0,
                playTime = 210000,
                isPlaying = false,
                music = "nextlevel",
                coverImg = R.drawable.img_first_album_default,
                isLike = false,
                albumIdx = 3
            )
        )
        songDao.insert(
            Song(
                title = "Savage",
                singer = "에스파 (AESPA)",
                second = 0,
                playTime = 200000,
                isPlaying = false,
                music = "savage",
                coverImg = R.drawable.img_first_album_default,
                isLike = false,
                albumIdx = 3
            )
        )

        Log.d("DB", "Dummy Data Inserted into RoomDB (ms unit)")
    }

    fun updateMiniPlayer() {
        val currentSong = MusicPlayerSingleton.currentSong
        val mediaPlayer = MusicPlayerSingleton.mediaPlayer

        if (currentSong != null && mediaPlayer != null) {
            try {
                binding.miniplayerRootLayout.visibility = View.VISIBLE
                binding.homePannelPlayingNowTv.text = "${currentSong.title} - ${currentSong.singer}"

                val duration = mediaPlayer.duration
                if (duration > 0) {
                    binding.homeMiniplayerSeekBar.max = duration
                    binding.homeMiniplayerSeekBar.progress = mediaPlayer.currentPosition
                }

                if (MusicPlayerSingleton.isPlaying) {
                    binding.homePannelMiniplayerPlayIv.setImageResource(R.drawable.btn_miniplay_mvpause)
                } else {
                    binding.homePannelMiniplayerPlayIv.setImageResource(R.drawable.btn_miniplayer_play)
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "updateMiniPlayer 오류 발생", e)
            }
        } else {
            binding.miniplayerRootLayout.visibility = View.GONE
        }
    }
}