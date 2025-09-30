package com.example.imreallystupid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imreallystupid.databinding.FragmentAlbumBinding



class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fragmentContainer,
                HomeFragment()).commitAllowingStateLoss()

            binding.albumTitleTv.text = arguments?.getString("title")
            binding.albumSingerTv.text = arguments?.getString("singer")
        }

        return binding.root
    }
}