package com.example.mission2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission2.databinding.ItemSalbumBinding

class SalbumRVAdapter(private val salbumList: ArrayList<Salbum>): RecyclerView.Adapter<SalbumRVAdapter.ViewHolder>() {

    var onRemoveClick: ((Int) -> Unit)? = null
    var onItemPlayClick: ((Int) -> Unit)? = null
    var onItemPauseClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSalbumBinding=
            ItemSalbumBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = salbumList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(salbumList[position])
    }

    fun removeItem(position:Int){
        salbumList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, salbumList.size)
    }

    inner class ViewHolder(val binding: ItemSalbumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(salbum: Salbum) {
            binding.itemSalbumTitleTv.text = salbum.title
            binding.itemSalbumInfoTv.text = salbum.info
            binding.itemSalbumCoverIv.setImageResource(salbum.coverImg!!)

            if (salbum.isPlaying) {
                binding.itemSalbumPlayIv.visibility = View.GONE
                binding.itemSalbumPauseIv.visibility = View.VISIBLE
            } else {
                binding.itemSalbumPlayIv.visibility = View.VISIBLE
                binding.itemSalbumPauseIv.visibility = View.GONE
            }

            binding.itemSalbumPlayIv.setOnClickListener {
                onItemPlayClick?.invoke(adapterPosition)
            }

            binding.itemSalbumPauseIv.setOnClickListener {
                onItemPauseClick?.invoke(adapterPosition)
            }

            binding.itemSalbumMoreIv.setOnClickListener {
                onRemoveClick?.invoke(adapterPosition)
            }
        }
    }
}