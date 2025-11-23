package com.example.mission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mission2.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(),HomeFragment.OnSongPlayListener {

    lateinit var binding: ActivityMainBinding
    private var song:Song = Song()
    private var gson:Gson= Gson()

    val songs=arrayListOf<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()

        binding.mainPlayerCl.setOnClickListener {
            val editor=getSharedPreferences("song",MODE_PRIVATE).edit()
            editor.putInt("songId",song.id)
            editor.apply()

            val intent=Intent(this, SongActivity::class.java)
            startActivity(intent)

        }
        binding.mainNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.mainPreviousIv.setOnClickListener {
            moveSong(-1)
        }

    }
    override fun onSongPlayed(title: String?, singer: String?){
        binding.mainMiniplayerTitleTv.text = title
        binding.mainMiniplayerSingerTv.text = singer
        binding.mainMiniplayerTitleTv.text = title
        binding.mainMiniplayerSingerTv.text = singer
    }
    private fun moveSong(direct: Int) {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val nowPos = getPlayingSongPosition(songId)

        val nextPos = nowPos + direct

        val newSong = songs[nextPos]

        val editor = spf.edit()
        editor.putInt("songId", newSong.id)
        editor.apply()

        setMiniPlayer(newSong)

        this.song = newSong
    }


    private fun getPlayingSongPosition(songId:Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id==songId){
                return i
            }
        }
        return 0
    }
    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
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
    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text=song.title
        binding.mainMiniplayerSingerTv.text=song.singer
        binding.mainMiniplayerProgressSb.progress=(song.second*100000)/song.playTime
    }

    override fun onStart() {
        super.onStart()

        val spf=getSharedPreferences("song",MODE_PRIVATE)
        val songId=spf.getInt("songId",0)

        val songDB=SongDatabase.getInstance(this)!!

            song=if(songId==0) {
            songDB.songDao().getSong(1)
            }
            else{
                songDB.songDao().getSong(songId)
            }
            Log.d("song ID",song.id.toString()) // 데이터 렌더링

        setMiniPlayer(song)
    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val allSongs = songDB.songDao().getSongs()

        if (allSongs.isNotEmpty()) {
            return
        }
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
                "Boy with Luv",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_boy",
                R.drawable.img_album_exp4,
                false
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

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.AlbumDao().getAlbums()

        if (albums.isNotEmpty()) {
            return
        }
        songDB.AlbumDao().insert(
            Album(
                0,
                "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2
            )
        )

        songDB.AlbumDao().insert(
            Album(
                1,
                "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )

        songDB.AlbumDao().insert(
            Album(
                2,
                "iScreaM Vol.10 : Next Level Remixes", "에스파 (AESPA)", R.drawable.img_album_exp3
            )
        )

        songDB.AlbumDao().insert(
            Album(
                3,
                "MAP OF THE SOUL : PERSONA", "방탄소년단 (BTS)", R.drawable.img_album_exp4
            )
        )

        songDB.AlbumDao().insert(
            Album(
                4,
                "GREAT!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5
            )
        )

    }

}