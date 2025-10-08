package com.rkdgudrn4094.week2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rkdgudrn4094.week2.databinding.FragmentAlbumBinding
import com.rkdgudrn4094.week2.databinding.FragmentSongBinding

class SongFragment: Fragment() {
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.songMixBtn.setOnClickListener {
            val result = Bundle().apply {
                putInt("imgRes", R.drawable.img_album_exp3)
            }
            parentFragmentManager.setFragmentResult("changeAlbumImage", result)
        }

        return binding.root
    }
}