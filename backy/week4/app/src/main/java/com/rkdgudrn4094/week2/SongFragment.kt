package com.rkdgudrn4094.week2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rkdgudrn4094.week2.databinding.FragmentAlbumBinding
import com.rkdgudrn4094.week2.databinding.FragmentSongBinding

class SongFragment: Fragment() {
    lateinit var binding: FragmentSongBinding
    private var songDatas = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        songDatas.apply{
            add(Song("Butter", "방탄소년단 (BTS)"))
            add(Song("Lilac", "아이유 (IU)"))
            add(Song("Next Level", "에스파 (AESPA)"))
            add(Song("Boy with Luv", "방탄소년단 (BTS)"))
            add(Song("BBoom BBoom", "모모랜드 (MOMOLAND)"))
            add(Song("Weekend", "태연 (Tae Yeon)"))
        }


        val songRVAdapter = SongRVAdapter(songDatas)
        binding.songSongRv.adapter = songRVAdapter
        binding.songSongRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)


        binding.songMixBtn.setOnClickListener {
            val result = Bundle().apply {
                putInt("imgRes", R.drawable.img_album_exp3)
            }
            parentFragmentManager.setFragmentResult("changeAlbumImage", result)
        }

        return binding.root
    }

    private fun changeSongFragment(song: Song) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val songJson = gson.toJson(song)
                    putString("song", songJson)
                }
            })
            .commitAllowingStateLoss()
    }
}