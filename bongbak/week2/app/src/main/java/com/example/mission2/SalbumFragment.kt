package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission2.databinding.FragmentSalbumBinding

class SalbumFragment: Fragment() {
    lateinit var binding: FragmentSalbumBinding
    private val salbumDatas = ArrayList<Salbum>()
    private lateinit var salbumRVAdapter: SalbumRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSalbumBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        salbumDatas.apply {
            add(Salbum("Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
            add(Salbum("LILAC", "아이유(IU)", R.drawable.img_album_exp2))
            add(Salbum("Butter(instrumental)", "한로로", R.drawable.img_album_exp3))
            add(Salbum("Permission to Dance(instrumental)", "한로로", R.drawable.img_album_exp4))
        }

        salbumRVAdapter = SalbumRVAdapter(salbumDatas)
        binding.lockerSalbumRv.adapter = salbumRVAdapter
        binding.lockerSalbumRv.layoutManager = LinearLayoutManager(context)

        salbumRVAdapter.onItemPlayClick = { position ->
            salbumDatas[position].isPlaying = true
            salbumRVAdapter.notifyItemChanged(position)
        }

        salbumRVAdapter.onItemPauseClick = { position ->
            salbumDatas[position].isPlaying = false
            salbumRVAdapter.notifyItemChanged(position)
        }

        salbumRVAdapter.onRemoveClick = { position ->
            salbumRVAdapter.removeItem(position)
        }
    }
}