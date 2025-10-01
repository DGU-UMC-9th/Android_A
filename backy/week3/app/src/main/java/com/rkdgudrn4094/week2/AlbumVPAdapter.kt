package com.rkdgudrn4094.week2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val detailFragment = DetailFragment()

    override fun createFragment(position: Int): androidx.fragment.app.Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> detailFragment
            else -> VideoFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    /*
    fun sendBundle(bundle: android.os.Bundle) : DetailFragment{
        val tmp = DetailFragment()
        tmp.arguments = bundle
        return tmp
    }*/
    fun sendData(bundle: android.os.Bundle){
        detailFragment.arguments=bundle
    }
}