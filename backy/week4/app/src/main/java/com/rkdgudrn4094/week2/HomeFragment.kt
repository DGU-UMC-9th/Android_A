package com.rkdgudrn4094.week2

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.rkdgudrn4094.week2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    private var listener : HomeFragmentDataListener?= null
    //lateinit var timer: Timer
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var sliderRunnable: Runnable
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        albumDatas.apply{
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        //startTimer()


        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onSyncAlbum(album: Album) {
                listener?.onDataReceived(album)
            }
        })


        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_first_album_default))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_first_album_default))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_first_album_default))
        binding.homeBannerVp.adapter=bannerAdapter
        binding.homeBannerVp.orientation= ViewPager2.ORIENTATION_HORIZONTAL

        startAutoSlider(bannerAdapter.itemCount)

        val viewPager = binding.homeBannerVp
        binding.homeBannerCi.setViewPager(viewPager)

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentDataListener){
            listener = context
        }
    }

    /*
    inner class Timer(): Thread(){
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try{
                while (true){

                    sleep(50)
                    mills +=50
                    if (mills % 3000 == 0f){
                        binding.homeBannerVp.setCurrentItem((binding.homeBannerVp.currentItem + 1))
                        mills = 0f
                        Log.d("Home", "@@MILLS IS 3000@@")
                    }


                }
            }catch (e: InterruptedException){
                Log.d("Song", "thread dead ${e.message}")
            }

        }
    }

    private fun startTimer(){
        timer = Timer()
        timer.start()
    }

     */

    private fun startAutoSlider(size: Int){
        sliderRunnable = Runnable{
            val viewPager = binding.homeBannerVp
            viewPager.currentItem = (viewPager.currentItem + 1) % size
            handler.postDelayed(sliderRunnable, 3000)
        }
        handler.postDelayed(sliderRunnable, 3000)
    }
}

