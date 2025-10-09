package com.example.mission2
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mission2.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    var albumTitle:String=""
    var composer:String=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        val fullAlbumTitleText = "이 앨범의 이름은 \"${albumTitle}\" 입니다."
        val fullComposerText = "이 앨범의 작곡가는 \"${composer}\" 입니다."
        binding.detailAlbumTitleTv.text = fullAlbumTitleText
        binding.detailComposerTv.text = fullComposerText

        return binding.root
    }
}