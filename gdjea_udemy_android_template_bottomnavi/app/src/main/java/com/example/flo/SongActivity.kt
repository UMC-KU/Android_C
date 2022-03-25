package com.example.flo

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding

lateinit var binding: ActivitySongBinding

class SongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false) //play 버튼을 터치했을 때 play 버튼은 사라지고 pause 버튼이 나타나야 하므로 해당 케이스는 isPlaying = false
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true) //pause 버튼을 터치했을 때 pause 버튼은 사라지고 play 버튼이 나타나야 하므로 해당 케이스는 isPlaying = true
        }

        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songMusicSingerTv.text = intent.getStringExtra("singer")
        }
    }
}

fun setPlayerStatus(isPlaying : Boolean){

    if(isPlaying){
        binding.songMiniplayerIv.visibility = View.VISIBLE
        binding.songPauseIv.visibility = View.GONE
    }
    else{
        binding.songMiniplayerIv.visibility = View.GONE
        binding.songPauseIv.visibility = View.VISIBLE
    }
}