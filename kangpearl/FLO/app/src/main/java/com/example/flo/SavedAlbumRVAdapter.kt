package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSavedAlbumBinding

class SavedAlbumRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<SavedAlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onRemoveAlbum(albumId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSavedAlbumBinding = ItemSavedAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])

        holder.binding.itemSavedAlbumMoreIv.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                mItemClickListener.onRemoveAlbum(albumList[pos].id)
                removeAlbum(pos)
            }
        }
    }

    override fun getItemCount(): Int = albumList.size

    private fun removeAlbum(position: Int) {
        albumList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, albumList.size)
    }

    inner class ViewHolder(val binding: ItemSavedAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemSavedAlbumTitleTv.text = album.title
            binding.itemSavedAlbumSingerTv.text = album.singer
            album.coverImg?.let { binding.itemSavedAlbumCoverIv.setImageResource(it) }
        }
    }
}