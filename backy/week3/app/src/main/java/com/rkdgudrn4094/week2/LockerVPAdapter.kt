package com.rkdgudrn4094.week2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SavedSongFragment()
            1 -> SongFileFragment()
            else -> SavedAlbumFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}