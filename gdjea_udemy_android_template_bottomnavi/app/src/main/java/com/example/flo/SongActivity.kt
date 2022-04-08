package com.example.flo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding
import java.nio.channels.InterruptedByTimeoutException

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer

    var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus()
        }

    }

    private fun initSong(){
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songMusicSingerTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus()
    }

    private fun setPlayerStatus(){

        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplay_pause)
            isPlaying = false
        }
        else{
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplayer_play)
            isPlaying = true
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true): Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run(){
            super.run()
            try { //isPlaying으로 Thread 동작, 정지를 간접적으로 결정하긴 했으나, 사실 while 문은 계속 돌고 있으니 비효율적. 프로그램이 종료되지 않으면서도 오류를 강제적으로 내고, 그 때 catch문이 실행되게끔 하는 try catch 문 사용
                while (true){

                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                        if (mills % 1000 == 0f){
                            runOnUiThread{
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song", "Thread가 죽었습니다. ${e.message}")
            }
        }
    }

}
