package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imreallystupid.databinding.FragmentDetailBinding
import com.example.imreallystupid.databinding.FragmentSongBinding


class SongFragment : Fragment() {
    lateinit var binding: FragmentSongBinding

    fun setToggleStatus(isPlaying : Boolean) {
        if(isPlaying) {
            binding.albumSongMixToggleOnIv.visibility = View.VISIBLE
            binding.albumSongMixToggleOffIv.visibility = View.GONE
        }
        else {
            binding.albumSongMixToggleOnIv.visibility = View.GONE
            binding.albumSongMixToggleOffIv.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.albumSongMixToggleOffIv.setOnClickListener {
            setToggleStatus(true)
        }
        binding.albumSongMixToggleOnIv.setOnClickListener {
            setToggleStatus(false)
        }

        return binding.root
    }

}