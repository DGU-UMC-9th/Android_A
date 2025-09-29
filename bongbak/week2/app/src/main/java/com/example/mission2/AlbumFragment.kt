package com.example.mission2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mission2.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstance : Bundle?
    ): View?{
        binding = FragmentAlbumBinding.inflate(inflater,container, false)
        return super.onCreateView(inflater, container, savedInstance)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()

        }
        binding.songLalacLayout.setOnClickListener{
            Toast.makeText(activity, "LILAC",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}