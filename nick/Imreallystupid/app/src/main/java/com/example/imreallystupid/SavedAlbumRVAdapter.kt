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

class SavedAlbumRVAdapter(private val albumlist: ArrayList<Album>) : RecyclerView.Adapter<SavedAlbumRVAdapter.ViewHolder>() {

    private val songs = ArrayList<Song>()
    interface SavedAlbumOnclickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }
    private lateinit var mItemClickListener: SavedAlbumOnclickListener

    fun setMyItemClickListener(itemClickListener: SavedAlbumOnclickListener){
        mItemClickListener = itemClickListener
    }

    fun removeitem(position: Int){
        albumlist.removeAt(position)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedAlbumRVAdapter.ViewHolder {
        val binding: ItemLockersavedalbumBinding = ItemLockersavedalbumBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumlist[position])
        holder.binding.lockerSavedAlbumPlayIv.setOnClickListener { holder.setSavedAlbumPlayerStatus(true) }
        holder.binding.lockerSavedAlbumPauseIv.setOnClickListener { holder.setSavedAlbumPlayerStatus(false) }
        holder.binding.lockerSavedAlbumMoreIv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = albumlist.size
    inner class ViewHolder(val binding: ItemLockersavedalbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.lockerSavedAlbumTitleTv.text = album.title
            binding.lockerSavedAlbumSingerTv.text = album.singer
            binding.lockerSavedAlbumAlbumImgIv.setImageResource(album.coverImg!!)
            binding.lockerSavedAlbumPlayIv.visibility = View.VISIBLE
            binding.lockerSavedAlbumPauseIv.visibility = View.GONE
        }

        fun setSavedAlbumPlayerStatus(isPlaying : Boolean) {
            if(isPlaying) {
                binding.lockerSavedAlbumPlayIv.visibility = View.GONE
                binding.lockerSavedAlbumPauseIv.visibility = View.VISIBLE
            }
            else {
                binding.lockerSavedAlbumPlayIv.visibility = View.VISIBLE
                binding.lockerSavedAlbumPauseIv.visibility = View.GONE
            }
        }
    }
}