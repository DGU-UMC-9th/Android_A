package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mission2.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment(){

    private val information=arrayListOf("저장한 곡","음악파일","저장앨범")

    lateinit var binding: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

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