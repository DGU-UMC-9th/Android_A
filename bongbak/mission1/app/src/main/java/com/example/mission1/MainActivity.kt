package com.example.mission1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mission1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnYellowIb.setOnClickListener{
            binding.txtYelloTv.setTextColor(ContextCompat.getColor(this,R.color.red))
            Toast.makeText(this,"노란색 버튼이 눌렸습니다",Toast.LENGTH_LONG).show()
        }
        binding.btnBlueIb.setOnClickListener{
            binding.txtBlueTv.setTextColor(ContextCompat.getColor(this,R.color.red))
            Toast.makeText(this,"하늘색 버튼이 눌렸습니다",Toast.LENGTH_LONG).show()
        }
        binding.btnPurpleIb.setOnClickListener{
            binding.txtPurpleTv.setTextColor(ContextCompat.getColor(this,R.color.red))
            Toast.makeText(this,"보라색 버튼이 눌렸습니다",Toast.LENGTH_LONG).show()
        }
        binding.btnGreenIb.setOnClickListener{
            binding.txtGreenTv.setTextColor(ContextCompat.getColor(this,R.color.red))
            Toast.makeText(this,"초록색 버튼이 눌렸습니다",Toast.LENGTH_LONG).show()
        }
        binding.btnRedIb.setOnClickListener{
            binding.txtRedTv.setTextColor(ContextCompat.getColor(this,R.color.red))
            Toast.makeText(this,"빨간색 버튼이 눌렸습니다",Toast.LENGTH_LONG).show()
        }

    }

}