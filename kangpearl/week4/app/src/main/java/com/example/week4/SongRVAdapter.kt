package com.example.week4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week4.Song
import com.example.week4.databinding.ItemSongBinding

class SongRVAdapter(private val songList: ArrayList<Song>) : RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position], position)
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song, position: Int){
            binding.itemSongNumberTv.text = String.format("%02d", position + 1)
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
        }
    }
}