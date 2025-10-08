package com.rkdgudrn4094.week2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rkdgudrn4094.week2.databinding.FragmentDetailBinding

class DetailFragment: Fragment() {
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        var titleText = arguments?.getString("title")
        var singerText = arguments?.getString("singer")

        titleText = "이 앨범의 이름은 ${titleText} 입니다."
        singerText = "이 앨범의 작곡가는 ${singerText} 입니다."


        binding.detailTitleTv.text = titleText
        binding.detailSingerTv.text = singerText

        //binding.detailSingerTv.text = arguments?.getString("title")
        //binding.detailTitleTv.text = arguments?.getString("singer")

        return binding.root
    }
}