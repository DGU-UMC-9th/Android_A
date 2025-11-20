package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLookBinding

class LookFragment : Fragment() {

    lateinit var binding: FragmentLookBinding
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLookBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val chartSongs = songDB.songDao().getSongs() as ArrayList
        val songRVAdapter = SongRVAdapter(chartSongs)

        binding.lookFloChartRv.adapter = songRVAdapter
        binding.lookFloChartRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}