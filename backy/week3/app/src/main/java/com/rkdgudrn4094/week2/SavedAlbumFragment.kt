package com.rkdgudrn4094.week2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rkdgudrn4094.week2.databinding.FragmentSavedalbumBinding


class SavedAlbumFragment: Fragment(){
    lateinit var binding: FragmentSavedalbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedalbumBinding.inflate(inflater, container, false)

        return binding.root
    }
}