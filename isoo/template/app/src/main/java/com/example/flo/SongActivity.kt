package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer

    // for playing img resource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDownBtn.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(song.isPlaying)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong() {
        if(intent.hasExtra("song")) {
            song = intent.getSerializableExtra("song") as Song;
        }
        startTimer()
    }

    private fun setPlayer(song : Song) {
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        setPlayerStatus(song.isPlaying)
        Log.d("song", song.title + song.singer)
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    fun setPlayerStatus(playing : Boolean) {
        timer.isPlaying = song.isPlaying
        if(playing) {
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplay_pause)
            song.isPlaying = false
        } else {
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplayer_play)
            song.isPlaying = true
        }
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread() {
        private var second : Int = 0;
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while(true) {
                    if(second >= playTime)
                        break
                    if(isPlaying) {
                        sleep(50)
                        mills += 50
                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }
                        if(mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e : InterruptedException) {
                Log.d("Song", "Thread is interrupted ${e.message}")
            }
        }
    }
}