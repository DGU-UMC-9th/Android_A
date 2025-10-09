package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.example.imreallystupid.databinding.FragmentLockerview1Binding
>>>>>>> origin/33-mission-4주차-미션-제출
=======
>>>>>>> a57410f1eabcb89a8497eb1752ab2af4f54be786
import com.example.imreallystupid.databinding.FragmentLockerview2Binding

class LockerViewFragment2 : Fragment() {

    lateinit var binding: FragmentLockerview2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerview2Binding.inflate(inflater, container, false)

        return binding.root
    }
}