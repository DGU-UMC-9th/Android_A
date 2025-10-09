package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.imreallystupid.databinding.FragmentHomeBinding
import com.google.gson.Gson


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumData = ArrayList<Album>()


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

        albumData.apply {
            add(Album("butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

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

        return binding.root
    }
}



