package com.example.flo

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding

lateinit var binding: ActivitySongBinding
var playing = true

class SongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus()
        }


        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songMusicSingerTv.text = intent.getStringExtra("singer")
        }
    }
}

fun setPlayerStatus(){

    if(playing){
        binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplay_pause)
        playing = false
    }
    else{
        binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplayer_play)
        playing = true
    }
}