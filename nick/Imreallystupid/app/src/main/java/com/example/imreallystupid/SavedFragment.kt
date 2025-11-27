package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imreallystupid.databinding.FragmentSavedBinding
import com.google.gson.Gson


class SavedFragment : Fragment() {

    lateinit var binding: FragmentSavedBinding
    lateinit var songDB: SongDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.lockerSavedVp.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val lockerRVAdapter = LockerRVAdapter()

        lockerRVAdapter.setMyItemClickListener(object  : LockerRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false,songId)
            }
        })

        binding.lockerSavedVp.adapter = lockerRVAdapter
        lockerRVAdapter.addSong(songDB.songDao().getLikedSongs(true)as ArrayList<Song>)
    }
}