package com.example.imreallystupid

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LockerViewFragment1()
            1 -> LockerViewFragment2()
            else -> LockerViewFragment3()
        }
    }

    override fun getItemCount(): Int = 3
}