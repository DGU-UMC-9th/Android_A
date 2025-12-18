package com.example.imreallystupid

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imreallystupid.AlbumRVAdapter.AlbumListItemClickListener
import com.example.imreallystupid.databinding.ActivitySongBinding
import com.example.imreallystupid.databinding.FragmentSavedalbumBinding
import com.example.imreallystupid.databinding.ItemAlbumBinding
import com.example.imreallystupid.databinding.ItemLockersavedalbumBinding

class SavedAlbumRVAdapter: RecyclerView.Adapter<SavedAlbumRVAdapter.ViewHolder>() {

    private var albums = ArrayList<Album>()
    interface MyItemClickListener{
        fun onRemoveAlbum(albumId: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedAlbumRVAdapter.ViewHolder {
        val binding: ItemLockersavedalbumBinding = ItemLockersavedalbumBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.binding.lockerSavedAlbumMoreIv.setOnClickListener {
            mItemClickListener.onRemoveAlbum(albums[position].id)
            removeAlbum(position)
        }
    }

    override fun getItemCount(): Int = albums.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbum(albums: ArrayList<Album>) {
        this.albums.clear()
        this.albums.addAll(albums)

        notifyDataSetChanged()
    }

    @SuppressLint
    private fun removeAlbum(position: Int) {
        albums.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockersavedalbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.lockerSavedAlbumTitleTv.text = album.title
            binding.lockerSavedAlbumSingerTv.text = album.singer
            binding.lockerSavedAlbumAlbumImgIv.setImageResource(album.coverImg!!)
        }
    }
}