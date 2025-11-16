package com.example.flo
//테스트//테스트
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val savedSongs = songDB.songDao().getLikedSongs(true) as ArrayList

        val adapter = SavedSongRVAdapter(savedSongs)
        binding.lockerSavedSongRv.adapter = adapter
        binding.lockerSavedSongRv.layoutManager = LinearLayoutManager(context)

        adapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                val song = songDB.songDao().getSong(songId)
                song.isLike = false
                songDB.songDao().update(song)
            }
        })
    }
}