package com.example.imreallystupid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.imreallystupid.databinding.FragmentHomeBinding
import com.google.gson.Gson
import kotlinx.coroutines.Runnable


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumData = ArrayList<Album>()
    private var currentPage = 0
    private var pageCount = 3
    private lateinit var viewpager : ViewPager2

    private lateinit var songDB: SongDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //3주차 코드
        /*binding.homeTodayAlbumTitleTv.setOnClickListener {
            val song = Song(binding.homeTodayAlbumTitleTv.text.toString(),binding.homeTodayAlbumSingerTv.text.toString())
            val sendData = Bundle().also {
                it.putString("title",song.title)
                it.putString("singer",song.singer)
            }
            val albumFragment = AlbumFragment().also {
                it.arguments = sendData
            }

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fragmentContainer,
                albumFragment).commitAllowingStateLoss()
        }*/
        songDB = SongDatabase.getInstance(requireContext())!!
        albumData.addAll(songDB.albumDao().getAlbums())

        val albumRVAdapter = AlbumRVAdapter(albumData)
        binding.homeTodayAlbumRv.adapter = albumRVAdapter
        binding.homeTodayAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.AlbumListItemClickListener{
            override fun onItemClick(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                 .replace(R.id.main_fragmentContainer, AlbumFragment().apply {
                     arguments = Bundle().apply {
                         val gson = Gson()
                         val albumJson = gson.toJson(album)
                         putString("album", albumJson)
                     }
                 })
                 .commitAllowingStateLoss()
            }
        })

        albumRVAdapter.sendToMiniPlayer(object : AlbumRVAdapter.AlbumPlayListener {
            override fun changeText(album: Album) {
                (activity as MainActivity).updateMiniPlayer(album)
            }
        })

        val HomeAdapter = HomeViewAdapter(this)
        binding.homeViewpagerVp.adapter = HomeAdapter
        binding.homeViewpagerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager = binding.homeViewpagerVp

        val indicator = binding.homeViewpagerIndicator
        indicator.setViewPager(viewpager)

        return binding.root
    }

    private val handler = Handler(Looper.getMainLooper())

    private val runnable : Runnable = object : Runnable {
        override fun run() {
            if(currentPage == pageCount) {
                currentPage = 0
            }
            viewpager.setCurrentItem(currentPage++, true)
            handler.postDelayed(this, 3000)
        }

    }
    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}



