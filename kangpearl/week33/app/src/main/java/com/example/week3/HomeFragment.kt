package com.example.week3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.week3.R
import com.example.week3.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // View Binding 초기화
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. ViewPager2 설정 함수 호출
        setupViewPager()

        // 2. 다른 뷰들의 클릭 리스너 설정 (예시)
        setupClickListeners()
    }

    // ViewPager2 설정 함수
    private fun setupViewPager() {
        val bannerImages = listOf(
            R.drawable.img_first_album_default,
            R.drawable.pngegg
        )

        val bannerAdapter = BannerAdapter(bannerImages)
        binding.homeBannerVp.adapter = bannerAdapter
    }

    // 기타 뷰들의 클릭 리스너 설정 (예시)
    private fun setupClickListeners() {
        // 미니 플레이어 전체 클릭 리스너 (예시)
        binding.miniplayerRootLayout.setOnClickListener {
            Toast.makeText(context, "미니 플레이어 클릭!", Toast.LENGTH_SHORT).show()
        }

        // 홈 버튼 클릭 리스너 (예시)
        binding.homeHomeSelectIv.setOnClickListener {
            Toast.makeText(context, "홈 탭 선택됨", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
