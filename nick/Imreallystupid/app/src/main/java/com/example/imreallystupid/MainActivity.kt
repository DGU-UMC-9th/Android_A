package com.example.imreallystupid

<<<<<<< HEAD
import android.content.Context
=======
>>>>>>> origin/33-mission-4주차-미션-제출
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActivityMainBinding
<<<<<<< HEAD
import com.google.gson.Gson
=======
>>>>>>> origin/33-mission-4주차-미션-제출


private lateinit var binding: ActivityMainBinding

<<<<<<< HEAD
private var albumData = ArrayList<Album>()
=======
>>>>>>> origin/33-mission-4주차-미션-제출
class MainActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

<<<<<<< HEAD
    fun updateMiniPlayer(album: Album) {
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
    }

=======
>>>>>>> origin/33-mission-4주차-미션-제출
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragmentContainer, HomeFragment(), null)
            .commit()

<<<<<<< HEAD
        binding.mainBottomnav.setOnItemSelectedListener { item ->
            when(item.itemId) {

                R.id.main_bottomnav_home_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                /*R.id.main_bottomnav_search_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.main_bottomnav_look_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }*/

                R.id.main_bottomnav_locker_it -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

=======
>>>>>>> origin/33-mission-4주차-미션-제출
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val reply = data?.getStringExtra("reply")
                Toast.makeText(this, reply, Toast.LENGTH_SHORT).show()
            }
        }
        val intent = Intent(this, SongActivity::class.java)

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())

        binding.mainMiniplayer.setOnClickListener {
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            resultLauncher.launch(intent)
        }

    }
}