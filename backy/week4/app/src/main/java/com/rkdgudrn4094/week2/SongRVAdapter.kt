package com.rkdgudrn4094.week2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rkdgudrn4094.week2.databinding.ItemAlbumBinding
import com.rkdgudrn4094.week2.databinding.ItemSongBinding

class SongRVAdapter(private val songList: ArrayList<Song>): RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(song: Song, pos: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SongRVAdapter.ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SongRVAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(songList[position], position)
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(songList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }


    inner class ViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song, pos: Int){
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongNumTv.text = (pos + 1).toString()
        }
    }
}