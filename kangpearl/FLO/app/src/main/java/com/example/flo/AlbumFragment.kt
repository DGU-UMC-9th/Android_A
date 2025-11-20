package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        isLiked = album.isLike
        setLikeImage(isLiked)

        binding.albumLikeIv.setOnClickListener {
            isLiked = !isLiked
            album.isLike = isLiked
            songDB.albumDao().update(album)
            setLikeImage(isLiked)
        }
    }

    private fun initSongRecyclerView(albumIdx: Int){
        songList.clear()
        songList.addAll(songDB.songDao().getSongsInAlbum(albumIdx))

        val songRVAdapter = SongRVAdapter(songList)
        binding.albumSongListRv.adapter = songRVAdapter
        binding.albumSongListRv.layoutManager = LinearLayoutManager(context)

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