package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()
    private val songList = ArrayList<Song>()
    private lateinit var songDB: SongDatabase
    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        setInit(album)
        initSongRecyclerView(album.id)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title
        binding.albumSingerNameTv.text = album.singer

        val dbAlbum = songDB.albumDao().getAlbum(album.id)
        isLiked = dbAlbum?.isLike ?: false
        setLikeImage(isLiked)

        binding.albumLikeIv.setOnClickListener {
            if (getJwt().isNotEmpty()) {
                isLiked = !isLiked
                songDB.albumDao().updateIsLikeById(isLiked, album.id)
                setLikeImage(isLiked)

                if (isLiked) {
                    Toast.makeText(context, "보관함에 저장되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "보관함에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getJwt(): String {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf?.getString("jwt", "") ?: ""
    }

    private fun initSongRecyclerView(albumIdx: Int){
        songList.clear()
        songList.addAll(songDB.songDao().getSongsInAlbum(albumIdx))

        val songRVAdapter = SongRVAdapter(songList)
        binding.albumSongListRv.adapter = songRVAdapter
        binding.albumSongListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        songRVAdapter.setMyItemClickListener(object : SongRVAdapter.MyItemClickListener {
            override fun onPlaySong(song: Song) {
                MusicPlayerSingleton.playSong(requireContext(), song)
                (activity as MainActivity).updateMiniPlayer()
            }
        })
    }

    private fun setLikeImage(isLike: Boolean){
        if(isLike){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }
}