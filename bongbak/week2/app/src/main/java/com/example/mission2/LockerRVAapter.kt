package com.example.mission2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission2.databinding.ItemSavedBinding

class LockerRVAdapter(private val savedList:ArrayList<Saved>): RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding: ItemSavedBinding =
            ItemSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAdapter.ViewHolder, position: Int) {

        holder.bind(savedList[position])
    }

    override fun getItemCount(): Int=savedList.size

    inner class ViewHolder(val binding: ItemSavedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(saved: Saved) {
            binding.itemSavedMusicTitle01Tv.text = saved.title
            binding.itemSavedSingerName01Tv.text = saved.singer
            binding.itemSavedCoverIv.setImageResource(saved.coverImg!!)
        }
    }
}