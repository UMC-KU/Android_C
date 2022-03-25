package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songBtnDownIv.setOnClickListener {
            finish()
        }
        binding.songBtnPlay32Iv.setOnClickListener {
                setPlayerstatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerstatus(true)
        }
        if (intent.hasExtra("title")&& intent.hasExtra("singer")){
            binding.songMainTitleTv.text=intent.getStringExtra("title")
            binding.songMainSingerTv.text=intent.getStringExtra("singer")
        }
    }

    fun setPlayerstatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songBtnPlay32Iv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE

        }
        else {
            binding.songBtnPlay32Iv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
}
