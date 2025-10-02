package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imreallystupid.databinding.FragmentHomeview2Binding

class HomeViewFragment2() : Fragment() {

    lateinit var binding : FragmentHomeview2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeview2Binding.inflate(inflater, container, false)

        return binding.root
    }
}