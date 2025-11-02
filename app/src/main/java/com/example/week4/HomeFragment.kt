package com.example.week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week4.Album
import com.example.week4.Song
import com.example.week4.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var albumRVAdapter: AlbumRVAdapter
    private val albumData = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initDummyData()

        initRecyclerView()

        binding.miniplayerRootLayout.setOnClickListener {
            (activity as? MainActivity)?.startActivity(android.content.Intent(activity, SongActivity::class.java))
        }

        return binding.root
    }

    private fun initRecyclerView() {
        albumRVAdapter = AlbumRVAdapter(albumData)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

            override fun onPlayClick(album: Album) {
                album.songs?.let {
                    if (it.isNotEmpty()) {
                        val firstSong = it[0]
                        binding.homePannelPlayingNowTv.text = "${firstSong.title}\n${firstSong.singer}"
                    }
                }
            }
        })
    }

    private fun initDummyData() {
        if(albumData.isEmpty()){
            val songs1 = arrayListOf(Song("LILAC", "아이유 (IU)"), Song("Coin", "아이유 (IU)"))
            albumData.add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2, songs1))

            val songs2 = arrayListOf(Song("Butter", "방탄소년단"), Song("Permission to Dance", "방탄소년단"))
            albumData.add(Album("Butter", "방탄소년단", R.drawable.img_album_exp, songs2))

            val songs3 = arrayListOf(Song("Weekend", "태연 (TAEYEON)"), Song("Fine", "태연 (TAEYEON)"))
            albumData.add(Album("Weekend", "태연 (TAEYEON)", R.drawable.img_album_exp2, songs3))

            val songs4 = arrayListOf(Song("Next Level", "aespa"), Song("Black Mamba", "aespa"))
            albumData.add(Album("Next Level", "aespa", R.drawable.img_album_exp, songs4))
        }
    }
}