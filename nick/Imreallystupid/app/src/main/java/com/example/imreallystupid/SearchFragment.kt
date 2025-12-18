package com.example.imreallystupid


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imreallystupid.databinding.FragmentSavedBinding
import com.example.imreallystupid.databinding.FragmentSearchBinding

class SearchFragment : androidx.fragment.app.Fragment() {

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

}