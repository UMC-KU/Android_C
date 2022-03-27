package com.example.flo

import android.os.Bundle
import android.os.PersistableBundle
<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding


class SongActivity : AppCompatActivity(){

    lateinit var binding : ActivitySongBinding
    //R.id.song_music_title_tv.text=intent.getStringExtra

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
<<<<<<< HEAD
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
        if(intent.hasExtra("title")&& intent.hasExtra("singer")){
            binding.songMusicTitleTv.text=intent.getStringExtra("title")
            binding.songSingerNameTv.text=intent.getStringExtra("singer")
=======
            setPlayerStatus(false)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
        }

    }


    private fun setPlayerStatus (isPlaying : Boolean){

        if(isPlaying){
<<<<<<< HEAD

=======
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE

        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE

        }

    }

}