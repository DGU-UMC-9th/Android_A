package com.example.imreallystupid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imreallystupid.databinding.ItemLockerBinding

class LockerRVAdapter( private val albumlist : ArrayList<Album> ) : RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    private lateinit var mItemClickListener: LockerRVAdapter.AlbumListItemClickListener

    interface AlbumListItemClickListener{
        fun onRemoveAlbum(position: Int)
    }

    fun setMyItemClickListener(itemClickListener: LockerRVAdapter.AlbumListItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun removeitem(position: Int){
        albumlist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockerRVAdapter.ViewHolder {
        val binding = ItemLockerBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumlist[position])
        holder.binding.lockerSavedMoreIv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = albumlist.size

    inner class ViewHolder(val binding: ItemLockerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.lockerSavedTitleTv.text = album.title
            binding.lockerSavedSingerTv.text = album.singer
            binding.lockerSavedCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}