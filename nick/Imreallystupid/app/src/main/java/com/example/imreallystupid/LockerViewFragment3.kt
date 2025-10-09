package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<< HEAD
=======
import com.example.imreallystupid.databinding.FragmentLockerview1Binding
>>>>>>> origin/33-mission-4주차-미션-제출
import com.example.imreallystupid.databinding.FragmentLockerview3Binding

class LockerViewFragment3 : Fragment() {

    lateinit var binding: FragmentLockerview3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerview3Binding.inflate(inflater, container, false)

        return binding.root
    }
}