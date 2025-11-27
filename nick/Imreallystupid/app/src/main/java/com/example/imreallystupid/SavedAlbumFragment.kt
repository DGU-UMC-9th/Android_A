package com.example.imreallystupid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imreallystupid.databinding.FragmentSavedBinding
import com.example.imreallystupid.databinding.FragmentSavedalbumBinding

class SavedAlbumFragment : Fragment() {

    lateinit var binding: FragmentSavedalbumBinding

    private var albums = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedalbumBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.lockerSavedAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val savedAlbumRVAdapter = SavedAlbumRVAdapter()

        savedAlbumRVAdapter.setMyItemClickListener(object : SavedAlbumRVAdapter.MyItemClickListener{
            override fun onRemoveAlbum(albumId: Int) {
                songDB.albumDao().getLikedAlbum(getJwt())
            }

        })

        binding.lockerSavedAlbumRv.adapter = savedAlbumRVAdapter
        savedAlbumRVAdapter.addAlbum(songDB.albumDao().getLikedAlbum(getJwt())as ArrayList<Album>)
    }

    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)

        return jwt
    }
}