package com.rkdgudrn4094.week2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkdgudrn4094.week2.databinding.FragmentSavedalbumBinding


class SavedAlbumFragment: Fragment(){
    lateinit var binding: FragmentSavedalbumBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedalbumBinding.inflate(inflater, container, false)
        albumDatas.apply{
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val savedAlbumRVAdapter = SavedAlbumRVAdapter(albumDatas)
        binding.savedAlbumAlbumRv.adapter = savedAlbumRVAdapter
        binding.savedAlbumAlbumRv.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        savedAlbumRVAdapter.setMyItemClickListener(object: SavedAlbumRVAdapter.MyItemClickListener{
            override fun onRemoveAlbum(position: Int) {
                savedAlbumRVAdapter.removeItem(position)
            }
        })

        return binding.root
    }
}