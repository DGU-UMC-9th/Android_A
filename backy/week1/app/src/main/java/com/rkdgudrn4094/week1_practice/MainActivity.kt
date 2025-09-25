package com.rkdgudrn4094.week1_practice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val img1 = findViewById<ImageView>(R.id.img1)
        val img2 = findViewById<ImageView>(R.id.img2)
        val img3 = findViewById<ImageView>(R.id.img3)
        val img4 = findViewById<ImageView>(R.id.img4)
        val img5 = findViewById<ImageView>(R.id.img5)

        var textColor = findViewById<TextView>(R.id.titleText)

        img1.setOnClickListener {
            textColor.setTextColor(ContextCompat.getColor(this, R.color.col1))
            Toast.makeText(this, "더없이 행복한 하루였어요", Toast.LENGTH_SHORT).show()
        }
        img2.setOnClickListener {
            textColor.setTextColor(ContextCompat.getColor(this, R.color.col2))
            Toast.makeText(this, "들뜨고 흥분돼요", Toast.LENGTH_SHORT).show()
        }
        img3.setOnClickListener {
            textColor.setTextColor(ContextCompat.getColor(this, R.color.col3))
            Toast.makeText(this, "평범한 하루였어요", Toast.LENGTH_SHORT).show()
        }
        img4.setOnClickListener {
            textColor.setTextColor(ContextCompat.getColor(this, R.color.col4))
            Toast.makeText(this, "생각이 많아지고 불안해요", Toast.LENGTH_SHORT).show()
        }
        img5.setOnClickListener {
            textColor.setTextColor(ContextCompat.getColor(this, R.color.col5))
            Toast.makeText(this, "부글부글 화가나요", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}