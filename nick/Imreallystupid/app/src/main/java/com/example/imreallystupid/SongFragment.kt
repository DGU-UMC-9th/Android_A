package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imreallystupid.databinding.FragmentDetailBinding
import com.example.imreallystupid.databinding.FragmentSongBinding


class SongFragment : Fragment() {
    lateinit var binding: FragmentSongBinding

    private var albumData = ArrayList<Album>()

    fun setToggleStatus(isPlaying : Boolean) {
        if(isPlaying) {
            binding.albumSongMixToggleOnIv.visibility = View.VISIBLE
            binding.albumSongMixToggleOffIv.visibility = View.GONE
        }
        else {
            binding.albumSongMixToggleOnIv.visibility = View.GONE
            binding.albumSongMixToggleOffIv.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.albumSongMixToggleOffIv.setOnClickListener {
            setToggleStatus(true)
        }
        binding.albumSongMixToggleOnIv.setOnClickListener {
            setToggleStatus(false)
        }

//        albumData.apply {
//            add(Album("butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
//        }

        val songAdapter = SongRVAdapter(albumData)
        binding.albumSongRv.adapter = songAdapter
        binding.albumSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

}