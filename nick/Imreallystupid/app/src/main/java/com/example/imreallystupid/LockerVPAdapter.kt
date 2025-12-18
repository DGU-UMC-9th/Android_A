package com.example.imreallystupid

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SavedFragment()
            1 -> LockerViewFragment2()
            else -> SavedAlbumFragment()
        }
    }

    override fun getItemCount(): Int = 3
}