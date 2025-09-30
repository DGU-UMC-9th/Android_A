package com.example.imreallystupid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imreallystupid.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        binding.homeTodayAlbumIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fragmentContainer,
                AlbumFragment()).commitAllowingStateLoss()

            val song = Song(binding.homeTodayAlbumTitleTv.text.toString(),binding.homeTodayAlbumSingerTv.text.toString())
            val sendData = Bundle().also {
                it.putString("title",song.title)
                it.putString("singer",song.singer)
            }

        }

        return binding.root
    }
}



