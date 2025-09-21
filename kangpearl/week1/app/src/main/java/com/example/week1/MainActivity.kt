package com.example.week1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton
import android.widget.TextView
import android.graphics.Color
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageButton3: ImageButton=findViewById(R.id.imageButton3)
        val textView8: TextView=findViewById(R.id.textView8)

        val imageButton5: ImageButton=findViewById(R.id.imageButton5)
        val textView9: TextView=findViewById(R.id.textView9)

        val imageButton6: ImageButton=findViewById(R.id.imageButton6)
        val textView10: TextView=findViewById(R.id.textView10)

        val imageButton7: ImageButton=findViewById(R.id.imageButton7)
        val textView11: TextView=findViewById(R.id.textView11)

        val imageButton4: ImageButton=findViewById(R.id.imageButton4)
        val textView13: TextView=findViewById(R.id.textView13)

        val duration = Toast.LENGTH_SHORT

        imageButton3.setOnClickListener {
            val text1="HAPPY"
            textView8.setTextColor(Color.YELLOW)
            val toast = Toast.makeText(this, text1, duration).show()
        }

        imageButton5.setOnClickListener {
            val text2="EXCITED"
            textView9.setTextColor(Color.CYAN)
            val toast = Toast.makeText(this, text2, duration).show()
        }

        imageButton6.setOnClickListener {
            val text3="NORMAL"
            textView10.setTextColor(Color.BLUE)
            val toast = Toast.makeText(this, text3, duration).show()
        }

        imageButton7.setOnClickListener {
            val text4="ANXIOUS"
            textView11.setTextColor(Color.GREEN)
            val toast = Toast.makeText(this, text4, duration).show()
        }

        imageButton4.setOnClickListener {
            val text5="ANGER"
            textView13.setTextColor(Color.RED)
            val toast = Toast.makeText(this, text5, duration).show()
        }
    }
}