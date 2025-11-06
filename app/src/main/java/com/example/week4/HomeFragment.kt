package com.example.week4

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.week4.Album
import com.example.week4.Song
import com.example.week4.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var albumRVAdapter: AlbumRVAdapter
    private val albumData = ArrayList<Album>()

    private val bannerData = arrayListOf(R.drawable.img_first_album_default, R.drawable.img_album_exp, R.drawable.img_album_exp2)
    private var currentBannerPosition = 0
    private val autoScrollHandler = Handler(Looper.getMainLooper())

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val newPosition = (currentBannerPosition + 1) % bannerData.size
            binding.homePannelBannerVp.setCurrentItem(newPosition, true)
            currentBannerPosition = newPosition

            autoScrollHandler.postDelayed(this, 3000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initDummyData()
        initRecyclerView()

        val bannerAdapter = BannerViewPagerAdapter(bannerData)
        binding.homePannelBannerVp.adapter = bannerAdapter
        binding.homePannelBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homePannelBannerVp.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentBannerPosition = position
            }
        })
        binding.homePannelIndicator.setViewPager(binding.homePannelBannerVp)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        autoScrollHandler.postDelayed(autoScrollRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    private fun initRecyclerView() {
        albumRVAdapter = AlbumRVAdapter(albumData)
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter

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
                        MusicPlayerSingleton.playSong(requireContext(), firstSong)
                    }
                }
            }
        })
    }

    private fun initDummyData() {
        val lilacSongs = arrayListOf(
            Song("LILAC", "아이유 (IU)", musicResId = R.raw.butter),
            Song("Flu", "아이유 (IU)", musicResId = R.raw.butter),
            Song("Coin", "아이유 (IU)", musicResId = R.raw.butter)
        )

        val butterSongs = arrayListOf(
            Song("Butter", "방탄소년단", musicResId = R.raw.butter),
            Song("Permission to Dance", "방탄소년단", musicResId = R.raw.butter)
        )

        val nextLevelSongs = arrayListOf(
            Song("Next Level", "aespa", musicResId = R.raw.butter),
            Song("Savage", "aespa", musicResId = R.raw.butter)
        )

        albumData.clear()
        albumData.add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2, lilacSongs))
        albumData.add(Album("Butter", "방탄소년단", R.drawable.img_album_exp, butterSongs))
        albumData.add(Album("Next Level", "aespa", R.drawable.img_first_album_default, nextLevelSongs))
        albumData.add(Album("Weekend", "태연 (TAEYEON)", R.drawable.img_album_exp2, null))
        albumData.add(Album("Celebrity", "아이유 (IU)", R.drawable.img_album_exp, null))
    }
}