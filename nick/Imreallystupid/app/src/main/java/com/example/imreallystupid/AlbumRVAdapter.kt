package com.example.imreallystupid

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imreallystupid.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val context : Context, private val albumlist: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {


    interface AlbumListItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    interface TodayAlbumItemClickListener{
        fun onItemClick(album: Album)
    }

    private lateinit var mItemClickListener: AlbumListItemClickListener
    fun setMyItemClickListener(itemClickListener: AlbumListItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun additem(album: Album) {
        albumlist.add(album)
        notifyDataSetChanged()
    }

    fun removeitem(position: Int){
        albumlist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumlist[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumlist[position]) }
        holder.binding.homeTodayAlbumArrowIv.setOnClickListener {  }
  //      holder.binding.homeTodayAlbumTitleTv.setOnClickListener { mItemClickListner.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = albumlist.size


    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.homeTodayAlbumTitleTv.text = album.title
            binding.homeTodayAlbumSingerTv.text = album.singer
            binding.homeTodayAlbumIv.setImageResource(album.coverImg!!)
            binding.homeTodayAlbumArrowIv.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("title",album.title)
                intent.putExtra("singer", album.singer)
                context.startActivity(intent)
            }
        }
    }

}