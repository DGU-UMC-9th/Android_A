package com.example.imreallystupid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imreallystupid.databinding.ItemLockerBinding

class LockerRVAdapter() : RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    private var songs = ArrayList<Song>()
    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockerRVAdapter.ViewHolder {
        val binding = ItemLockerBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.lockerSavedMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint
    fun addSong(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint
    private fun removeSong(position: Int) {
        songs.removeAt(position)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemLockerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song : Song) {
            binding.lockerSavedTitleTv.text = song.title
            binding.lockerSavedSingerTv.text = song.singer
            binding.lockerSavedCoverImgIv.setImageResource(song.coverImg!!)
        }
    }

}