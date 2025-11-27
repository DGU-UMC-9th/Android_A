package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedSongBinding

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentSavedSongBinding
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val savedSongs = songDB.songDao().getLikedSongs(true) as ArrayList
        val adapter = SavedSongRVAdapter(savedSongs)
        binding.savedSongRv.adapter = adapter
        binding.savedSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                val song = songDB.songDao().getSong(songId)
                song.isLike = false
                songDB.songDao().update(song)
            }
        })
    }
}