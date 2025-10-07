package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mission2.databinding.FragmentHomePannelBinding

class HomePannelFragment(val imgRes:Int): Fragment() {

    lateinit var binding: FragmentHomePannelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding=FragmentHomePannelBinding.inflate(inflater,container,false)

        binding.homePannelBackgroundIv.setImageResource(imgRes)
        return binding.root
    }
}