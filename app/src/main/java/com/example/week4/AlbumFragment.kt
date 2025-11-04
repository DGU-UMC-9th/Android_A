package com.example.week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week4.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val songData = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        initDummyData()

        initRecyclerView()

        binding.albumBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun initRecyclerView(){
        val songRVAdapter = SavedSongRVAdapter(songData)
        binding.albumSongListRv.adapter = songRVAdapter
        binding.albumSongListRv.layoutManager = LinearLayoutManager(context)
    }

    private fun initDummyData(){
        songData.add(Song("LILAC", "아이유 (IU)"))
        songData.add(Song("Flu", "아이유 (IU)"))
        songData.add(Song("Coin", "아이유 (IU)"))
        songData.add(Song("봄 안녕 봄", "아이유 (IU)"))
        songData.add(Song("Celebrity", "아이유 (IU)"))
        songData.add(Song("돌림노래", "아이유 (IU)"))
    }
}