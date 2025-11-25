package com.rkdgudrn4094.week2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rkdgudrn4094.week2.databinding.ItemLockerBinding

class SavedAlbumRVAdapter(/*private val albumList: ArrayList<Album>*/): RecyclerView.Adapter<SavedAlbumRVAdapter.ViewHolder>() {
    private val albums = ArrayList<Album>()
    interface MyItemClickListener{
        fun onRemoveAlbum(position: Int)
    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: SavedAlbumRVAdapter.MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    fun addAlbums(albums: ArrayList<Album>){
        this.albums.clear()
        this.albums.addAll(albums)

        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albums.removeAt(position)
        //albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SavedAlbumRVAdapter.ViewHolder {
        val binding: ItemLockerBinding = ItemLockerBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
        //holder.bind(albumList[position], position)
        holder.itemView.setOnClickListener {
            myItemClickListener.onRemoveAlbum(position)
            removeItem(position)
        }
    }

    override fun getItemCount(): Int {
        //return albumList.size
        return albums.size
    }

    inner class ViewHolder(val binding: ItemLockerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album/*, pos: Int*/){
            binding.itemLockerAlbumTv.text = album.title
            binding.itemLockerSingerTv.text = album.singer
            binding.itemLockerAlbumImgIv.setImageResource(album.coverImg!!)
        }
    }
}