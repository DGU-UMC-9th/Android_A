package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imreallystupid.databinding.FragmentSavedBinding
import com.example.imreallystupid.databinding.FragmentSavedalbumBinding

class SavedAlbumFragment : Fragment() {

    lateinit var binding: FragmentSavedalbumBinding

    private var albumData = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedalbumBinding.inflate(inflater, container, false)

        albumData.apply {
            add(Album("butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val savedAlbumRVAdapter = SavedAlbumRVAdapter(albumData)
        binding.lockerSavedAlbumRv.adapter = savedAlbumRVAdapter
        binding.lockerSavedAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        savedAlbumRVAdapter.setMyItemClickListener(object : SavedAlbumRVAdapter.SavedAlbumOnclickListener{
            override fun onItemClick(album: Album) {
                //이후 앨범 클릭 이벤트 적용
            }

            override fun onRemoveAlbum(position: Int) {
                savedAlbumRVAdapter.removeitem(position)
            }

        })
        return binding.root
    }
}