package com.example.week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week4.Song
import com.example.week4.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private val savedSongData = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        initDummyData()

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView(){
        val savedSongRVAdapter = SavedSongRVAdapter(savedSongData)
        binding.lockerSavedSongRv.adapter = savedSongRVAdapter
        binding.lockerSavedSongRv.layoutManager = LinearLayoutManager(context)
    }

    private fun initDummyData(){
        savedSongData.add(Song("Butter", "방탄소년단"))
        savedSongData.add(Song("Next Level", "aespa"))
        savedSongData.add(Song("Celebrity", "아이유 (IU)"))
        savedSongData.add(Song("Weekend", "태연 (TAEYEON)"))
        savedSongData.add(Song("LILAC", "아이유 (IU)"))
        savedSongData.add(Song("Permission to Dance", "방탄소년단"))
    }
}