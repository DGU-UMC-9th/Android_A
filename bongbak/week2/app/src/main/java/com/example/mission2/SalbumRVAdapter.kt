package com.example.mission2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mission2.databinding.ItemSalbumBinding
class SalbumRVAdapter(private val salbumList: ArrayList<Salbum>): RecyclerView.Adapter<SalbumRVAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onRemoveItem(position: Int)
    }
    private lateinit var myItemClickListener: SalbumRVAdapter.OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSalbumBinding=
            ItemSalbumBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)
    }
    override fun getItemCount(): Int = salbumList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(salbumList[position])
    }
    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        myItemClickListener=itemClickListener
    }
    fun removeItem(position:Int){
        salbumList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, salbumList.size)
    }
    inner class ViewHolder(val binding: ItemSalbumBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            // '더보기 버튼' 클릭 리스너 설정
            binding.itemSalbumMoreIv.setOnClickListener {
                val currentPosition = adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    //클릭 리스너 설정 확인
                    if (::myItemClickListener.isInitialized) {
                        myItemClickListener.onRemoveItem(currentPosition)
                    }
                }
            }
        }

        fun bind(salbum: Salbum) {
            binding.itemSalbumTitleTv.text = salbum.title

            binding.itemSalbumInfoTv.text = salbum.info

            binding.itemSalbumCoverIv.setImageResource(salbum.coverImg!!)
        }
    }
}