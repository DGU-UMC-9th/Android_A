package com.example.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission2.Song
import com.example.mission2.databinding.ItemSavedBinding


class LockerRVAapter() :
    RecyclerView.Adapter<LockerRVAapter.ViewHolder>() {
    private val songs = ArrayList<Song>()
    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerRVAapter.ViewHolder {
        val binding: ItemSavedBinding = ItemSavedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSavedMore01Iv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSavedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song){
            binding.itemSavedCoverIv.setImageResource(song.coverImg!!)
            binding.itemSavedMusicTitle01Tv.text = song.title
            binding.itemSavedSingerName01Tv.text = song.singer
        }
    }
}