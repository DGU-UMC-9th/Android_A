package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imreallystupid.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
<<<<<<< HEAD
<<<<<<< HEAD
import com.google.gson.Gson
=======
>>>>>>> origin/33-mission-4주차-미션-제출
=======
import com.google.gson.Gson
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786


class AlbumFragment : Fragment() {



    lateinit var binding: FragmentAlbumBinding
<<<<<<< HEAD
<<<<<<< HEAD
    private var gson: Gson = Gson()
=======
>>>>>>> origin/33-mission-4주차-미션-제출
=======
    private var gson: Gson = Gson()
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786
    private val information = arrayListOf("수록곡","상세정보","영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786
        //binding.albumTitleTv.text = arguments?.getString("title")
        //binding.albumSingerTv.text = arguments?.getString("singer")

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        setinit(album)
<<<<<<< HEAD
=======
        binding.albumTitleTv.text = arguments?.getString("title")
        binding.albumSingerTv.text = arguments?.getString("singer")
>>>>>>> origin/33-mission-4주차-미션-제출
=======
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786


        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fragmentContainer,
                HomeFragment()).commitAllowingStateLoss()
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> origin/33-mission-4주차-미션-제출
=======

>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786

    private fun setinit(album: Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumTitleTv.text = album.title.toString()
        binding.albumSingerTv.text = album.singer.toString()
    }
<<<<<<< HEAD
=======
>>>>>>> origin/33-mission-4주차-미션-제출
=======
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786
}