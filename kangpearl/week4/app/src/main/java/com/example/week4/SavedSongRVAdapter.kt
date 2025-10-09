package com.example.week4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week4.Song
import com.example.week4.databinding.ItemSavedSongBinding

class SavedSongRVAdapter(private var songList: ArrayList<Song>) : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSavedSongBinding = ItemSavedSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])

        holder.binding.itemSavedSongMoreIv.setOnClickListener {
            removeSong(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int = songList.size

    private fun removeSong(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(val binding: ItemSavedSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSavedSongTitleTv.text = song.title
            binding.itemSavedSongSingerTv.text = song.singer
            binding.itemSavedSongCoverIv.setImageResource(R.drawable.img_album_exp)
        }
    }
}