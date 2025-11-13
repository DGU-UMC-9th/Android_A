package com.example.imreallystupid

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActivityMainBinding
import com.google.gson.Gson


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var song: Song = Song()
    private var gson: Gson = Gson()
    val songs = arrayListOf<Song>()
    var nowPos = 0

    fun updateMiniPlayer(album: Album) {
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Imreallystupid)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySong()

        val songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragmentContainer, HomeFragment(), null)
            .commit()

        binding.mainBottomnav.setOnItemSelectedListener { item ->
            when(item.itemId) {

                R.id.main_bottomnav_home_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                /*R.id.main_bottomnav_search_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.main_bottomnav_look_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }*/

                R.id.main_bottomnav_locker_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }


//        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val data: Intent? = result.data
//                val sendTime = data?.getIntExtra("sendTime", 0)?.toFloat() ?: 0
//                val playTime = data?.getIntExtra("playTime", 0) ?: 0
//                val progress = if (playTime != 0) ((sendTime.toFloat() / playTime) * 10000).toInt() else 0
//                binding.mainMiniplayerSeekbarSb.progress = progress
//            }
//        }






        binding.mainMiniplayerNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.mainMiniplayerPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.mainMiniplayer.setOnClickListener {
            val editor = getSharedPreferences("song",MODE_PRIVATE).edit()
            editor.putInt("songId",songs[nowPos].id)
            Log.d("send",songs[nowPos].id.toString())
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }



    }
    private fun moveSong(direct: Int) {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()
        if (nowPos + direct < 0 ) {
            Toast.makeText(this,"first song",Toast.LENGTH_LONG).show()
            return
        }
        if (nowPos + direct >= songs.size) {
            Toast.makeText(this,"last song",Toast.LENGTH_LONG).show()
            return
        }
        nowPos += direct
        Log.d("check",nowPos.toString())
        setPlayer((songs[nowPos]))
    }

    private fun setPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerSeekbarSb.progress = (song.second * 1000 / song.playTime)
    }



    private fun inputDummySong() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp4,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }


    override fun onStart() {
        super.onStart()
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!
        Log.d("check1", songId.toString())
        song = if (songId == 0) {
            songDB.songDao().getSong(1)
        } else {
            songDB.songDao().getSong(songId)
        }
        Log.d("check1", song.toString())
        setMiniPlayer(song)

        if ((songId) == 0) {
            nowPos = 0
        } else {
            nowPos = (songId - 1)
        }

    }
    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerSeekbarSb.progress = ((song.second*10000)/song.playTime)
        Log.d("minip",binding.mainMiniplayerSeekbarSb.progress.toString())
    }
}