package com.rkdgudrn4094.week2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rkdgudrn4094.week2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeAlbumImgIv1.setOnClickListener {
            //(context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
            val sendData = Bundle().apply{
                putString("title", binding.homeAlbumTitleTv1.text.toString())
                putString("singer", binding.homeAlbumSingerTv1.text.toString())
            }
            val tmp = AlbumFragment()
            tmp.arguments = sendData

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, tmp).commitAllowingStateLoss()
        }
        return binding.root
    }
}