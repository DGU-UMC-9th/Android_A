package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission2.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    lateinit var binding: FragmentSavedBinding
    private val savedDatas = ArrayList<Saved>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false) // 바인딩 클래스 변경

        savedDatas.apply {
            add(Saved("Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
            add(Saved("Permission to Dance", "방탄소년단(BTS)", R.drawable.img_album_exp2))
            add(Saved("Butter(instrumental)", "방탄소년단(BTS)", R.drawable.img_album_exp3))
            add(Saved("Permission to Dance(instrumental)", "방탄소년단(BTS)", R.drawable.img_album_exp4))
        }

        val lockerRVAdapter = LockerRVAdapter(savedDatas)
        binding.lockerSavedRv.adapter = lockerRVAdapter
        binding.lockerSavedRv.layoutManager = LinearLayoutManager(context)

        lockerRVAdapter.setOnItemClickListener(object : LockerRVAdapter.OnItemClickListener {
            override fun onRemoveItem(position: Int) {
                lockerRVAdapter.removeItem(position)
            }
        })

        return binding.root
    }
}