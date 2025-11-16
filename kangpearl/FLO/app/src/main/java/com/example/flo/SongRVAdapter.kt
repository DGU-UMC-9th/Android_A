package com.example.flo
//테스트
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class SongRVAdapter(private val songList: ArrayList<Song>) : RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onPlaySong(song: Song)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position], position)

        holder.itemView.setOnClickListener {
            mItemClickListener.onPlaySong(songList[position])
        }
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song, position: Int){
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongNumberTv.text = String.format("%02d", position + 1)
        }
    }
}