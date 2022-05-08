package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    //lateinit var song : Song
    lateinit var timer : Timer

    private var mediaPlayer : MediaPlayer? = null
    private var gson : Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    // for playing img resource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        //setPlayer(song)
        initClickListener()

    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        //val songJson = gson.toJson(songs[nowPos])
        //editor.putString("songData",songJson)
        editor.putInt("songId", songs[nowPos].id)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // free resources
        mediaPlayer = null
    }

    private fun initPlayList() {
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener() {
        binding.songDownBtn.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(songs[nowPos].isPlaying)
        }

        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songLikeBtn.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun setLike(isLike : Boolean) {
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)
        if(!isLike) {
            binding.songLikeBtn.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeBtn.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun moveSong(direct: Int) {
        if(nowPos + direct < 0) {
            Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size) {
            Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct
        timer.interrupt()
        startTimer()
        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    private fun initSong() {
/*        if(intent.hasExtra("song")) {
            song = intent.getSerializableExtra("song") as Song;
        }*/
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song Id",songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId : Int): Int {
        for(i in 0 until songs.size) {
            if(songs[i].id == songId) {
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song : Song) {
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumImgIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music,"raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if(song.isLike) {
            binding.songLikeBtn.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeBtn.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
        Log.d("song", song.title + song.singer)
    }

    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    fun setPlayerStatus(playing : Boolean) {
        timer.isPlaying = songs[nowPos].isPlaying
        if(playing) {
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplay_pause)
            songs[nowPos].isPlaying = false
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplayer_play)
            songs[nowPos].isPlaying = true
            if(mediaPlayer?.isPlaying == true)
                mediaPlayer?.pause()
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