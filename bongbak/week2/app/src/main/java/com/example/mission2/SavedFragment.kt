package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.LockerRVAapter // (이전 파일의 LockerRVAapter를 사용한다고 가정)
import com.example.mission2.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    lateinit var binding: FragmentSavedBinding

    lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false) // 바인딩 클래스 변경

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val lockerRVAapter = LockerRVAapter()

        lockerRVAapter.setMyItemClickListener(object : LockerRVAapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false,songId)
            }
        })

        binding.lockerSavedRv.adapter = lockerRVAapter
        lockerRVAapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

}