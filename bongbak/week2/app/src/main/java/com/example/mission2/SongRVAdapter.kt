package com.example.mission2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission2.databinding.ItemSongBinding

class SongRVAdapter(private val includedList:ArrayList<Included>): RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding: ItemSongBinding =
            ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongRVAdapter.ViewHolder, position: Int) {

        holder.bind(includedList[position])
    }

    override fun getItemCount(): Int=includedList.size

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(included: Included) {
            val orderText=String.format("%02d",adapterPosition+1)
            binding.itemSongListOrder01Tv.text=orderText
            binding.itemSongMusicTitle01Tv.text = included.title
            binding.itemSongSingerName01Tv.text = included.singer
        }
    }
}