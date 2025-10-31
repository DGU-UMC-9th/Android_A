package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission2.databinding.FragmentSongBinding

class SongFragment : Fragment() {




    lateinit var binding: FragmentSongBinding

    private val includedDatas=ArrayList<Included>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        includedDatas.apply{
            add(Included("Butter","방탄소년단(BTS)"))
            add(Included("Permission to Dance","방탄소년단(BTS)"))
            add(Included("Butter(instrumental)","방탄소년단(BTS)"))
            add(Included("Permission to Dance(instrumental)","방탄소년단(BTS)"))
        }
        val songRVAdapter = SongRVAdapter(includedDatas)
        binding.albumAlbumContentRv.adapter = songRVAdapter
        binding.albumAlbumContentRv.layoutManager = LinearLayoutManager(context)

        binding.songMixoffTg.setOnClickListener {

            setMixStatus(true)
        }
        binding.songMixonTg.setOnClickListener {

            setMixStatus(false)
        }

        return binding.root
    }


    private fun setMixStatus(isMix: Boolean) {
        if (isMix) {
            binding.songMixonTg.visibility = View.VISIBLE
            binding.songMixoffTg.visibility = View.GONE
        } else {
            binding.songMixonTg.visibility = View.GONE
            binding.songMixoffTg.visibility = View.VISIBLE
        }
    }
}