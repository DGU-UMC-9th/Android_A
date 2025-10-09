package com.example.mission2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment:Fragment, val albumTitle: String, val composer:String) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> {
                val detailFragment = DetailFragment()

                detailFragment.albumTitle = this.albumTitle
                detailFragment.composer = this.composer

                detailFragment
            }
            2 -> VideoFragment()
            else -> SongFragment()
        }
    }
}