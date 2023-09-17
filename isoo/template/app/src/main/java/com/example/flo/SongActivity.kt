package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding

    // for playing img resource
    var playing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songDownBtn.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus()
        }

        if(intent.hasExtra("song")) {
            val song = intent.getSerializableExtra("song") as Song
            binding.songTitleTv.text = song.title
            binding.songSingerTv.text = song.singer
            Log.d("song", song.title + song.singer)
        }
    }

    fun setPlayerStatus() {
        if(playing) {
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplay_pause)
            playing = false
        } else {
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplayer_play)
            playing = true
        }
    }
}