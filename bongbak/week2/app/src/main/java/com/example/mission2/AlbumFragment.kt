package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mission2.databinding.FragmentAlbumBinding
import com.example.mission2.databinding.FragmentSongBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {

    private val information=arrayListOf("수록곡","상세정보","영상")

    lateinit var binding : FragmentAlbumBinding

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstance : Bundle?
    ): View?{
        binding = FragmentAlbumBinding.inflate(inflater,container, false)

        val titleFromTextView = binding.albumMusicTitleTv.text.toString()
        val composerFromTextView = binding.albumSingerNameTv.text.toString()

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        }
        val albumAdapter = AlbumVPAdapter(this,titleFromTextView,composerFromTextView)
        binding.albumContentVp.adapter=albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
       }
}
