package com.example.flo
//테스트
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemBannerBinding
class BannerViewPagerAdapter(private val bannerList: ArrayList<Int>) : RecyclerView.Adapter<BannerViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBannerBinding = ItemBannerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size

    inner class ViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageRes: Int) {
            binding.bannerImageIv.setImageResource(imageRes)
        }
    }
}