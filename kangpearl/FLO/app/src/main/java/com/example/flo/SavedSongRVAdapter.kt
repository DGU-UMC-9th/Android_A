package com.example.flo
//테스트
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSavedSongBinding

class SavedSongRVAdapter(private val songList: ArrayList<Song>) : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSavedSongBinding = ItemSavedSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])
        holder.binding.itemSavedSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songList[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songList.size

    private fun removeSong(position: Int) {
        songList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, songList.size)
    }

    inner class ViewHolder(val binding: ItemSavedSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSavedSongTitleTv.text = song.title
            binding.itemSavedSongSingerTv.text = song.singer
            song.coverImg?.let { binding.itemSavedSongCoverIv.setImageResource(it) }
        }
    }
}