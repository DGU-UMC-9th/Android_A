package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<< HEAD
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
=======
>>>>>>> origin/33-mission-4주차-미션-제출
import com.example.imreallystupid.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    private val information = arrayListOf("저장한 곡", "음악 파일", "저장 앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdaptor = LockerVPAdapter(this)
        binding.lockerViewpagerVp.adapter = lockerAdaptor
        TabLayoutMediator(binding.lockerTablayoutTb, binding.lockerViewpagerVp){
                tab, position ->
            tab.text = information[position]
        }.attach()

<<<<<<< HEAD

=======
>>>>>>> origin/33-mission-4주차-미션-제출
        return binding.root
    }
}
