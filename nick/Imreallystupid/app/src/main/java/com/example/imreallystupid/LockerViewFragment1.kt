package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imreallystupid.databinding.FragmentLockerview1Binding

class LockerViewFragment1 : Fragment() {

    lateinit var binding: FragmentLockerview1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerview1Binding.inflate(inflater, container, false)

        return binding.root
    }
}