package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mission2.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment(){

    private val information=arrayListOf("저장한 곡","음악파일","저장앨범")
    private val savedDatas=ArrayList<Saved>()
    lateinit var binding: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)
        savedDatas.apply{
            add(Saved("Butter","방탄소년단(BTS)",R.drawable.img_album_exp))
            add(Saved("Permission to Dance","방탄소년단(BTS)",R.drawable.img_album_exp2))
            add(Saved("Butter(instrumental)","방탄소년단(BTS)",R.drawable.img_album_exp3))
            add(Saved("Permission to Dance(instrumental)","방탄소년단(BTS)",R.drawable.img_album_exp4))
        }
        val lockerRVAdapter = LockerRVAdapter(savedDatas)
        binding.lockerSavedRv.adapter = lockerRVAdapter
        binding.lockerSavedRv.layoutManager = LinearLayoutManager(context)

        binding.lockerPlayAllImgIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                R.id.main_frm,
                HomeFragment()
            ).commitAllowingStateLoss()
        }
        val lockerAdapter = LockerVPAdapter(this)
        binding.vpLockerLockerFragment.adapter=lockerAdapter

        TabLayoutMediator(binding.lockerContentTb, binding.vpLockerLockerFragment) { tab, position ->
            tab.text = information[position]

        }.attach()

        return binding.root
    }
}