package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedAlbumBinding

class SavedAlbumFragment : Fragment() {
    lateinit var binding: FragmentSavedAlbumBinding
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedAlbumBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val savedAlbums = songDB.albumDao().getLikedAlbums(true) as ArrayList

        val adapter = SavedAlbumRVAdapter(savedAlbums)
        binding.savedAlbumRv.adapter = adapter
        binding.savedAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter.setMyItemClickListener(object : SavedAlbumRVAdapter.MyItemClickListener {
            override fun onRemoveAlbum(albumId: Int) {
                val album = songDB.albumDao().getAlbum(albumId)
                if (album != null) {
                    album.isLike = false
                    songDB.albumDao().update(album)
                }
            }
        })
    }
}