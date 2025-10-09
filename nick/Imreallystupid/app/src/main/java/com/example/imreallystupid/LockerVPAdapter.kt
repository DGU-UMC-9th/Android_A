package com.example.imreallystupid

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
<<<<<<< HEAD
<<<<<<< HEAD
            0 -> SavedFragment()
=======
            0 -> LockerViewFragment1()
>>>>>>> origin/33-mission-4주차-미션-제출
=======
            0 -> SavedFragment()
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786
            1 -> LockerViewFragment2()
            else -> LockerViewFragment3()
        }
    }

    override fun getItemCount(): Int = 3
}