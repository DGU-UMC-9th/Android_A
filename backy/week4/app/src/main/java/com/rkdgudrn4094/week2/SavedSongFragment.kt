package com.rkdgudrn4094.week2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkdgudrn4094.week2.databinding.FragmentSavedsongBinding

class SavedSongFragment: Fragment() {
    lateinit var binding: FragmentSavedsongBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentSavedsongBinding.inflate(inflater,container,false)
        albumDatas.apply{
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val savedSongRVAdapter = SavedSongRVAdapter(albumDatas)
        binding.savedsongSongRv.adapter = savedSongRVAdapter
        binding.savedsongSongRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        savedSongRVAdapter.setMyItemClickListener(object: SavedSongRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {

            }
            override fun onRemoveAlbum(position: Int){
                savedSongRVAdapter.removeItem(position)
            }
        })


        return binding.root
    }
}