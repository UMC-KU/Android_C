package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding
import java.nio.channels.InterruptedByTimeoutException
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
//    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
//        setPlayer(song)
        initClickListener()
    }

    //사용자 포커스를 잃었을 때 음악 중지
    override fun onPause() {
        super.onPause()
        isPlaying = false
        setPlayerStatus()

//        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터
//        val songJson = gson.toJson(song)
//        editor.putString("songData", songJson)
        editor.putInt("songId", songs[nowPos].id)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어플레이어 해제
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus()
        }

        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun initSong(){
//        if (intent.hasExtra("title") && intent.hasExtra("singer")){
//            song = Song(
//                intent.getStringExtra("title")!!,
//                intent.getStringExtra("singer")!!,
//                intent.getIntExtra("second", 0),
//                intent.getIntExtra("playTime", 0),
//                intent.getBooleanExtra("isPlaying", false),
//                intent.getStringExtra("music")!!
//            )
//        }
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString())

        startTimer()
        setPlayer(songs[nowPos])
    }


    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }


    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }
        else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }

    private fun moveSong(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this, "first song!", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this, "last song!", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release() //미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어플레이어 해제

        setPlayer(songs[nowPos])
    }

    private fun setPlayer(song : Song){
//        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
//        binding.songMusicSingerTv.text = intent.getStringExtra("singer")!!
//        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
//        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
//        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
//        val music = resources.getIdentifier(song.music, "raw", this.packageName)
//        mediaPlayer = MediaPlayer.create(this, music)
        binding.songMusicTitleTv.text = song.title
        binding.songMusicSingerTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }
        else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus()
    }

    private fun setPlayerStatus(){

//        song.isPlaying = isPlaying
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplay_pause)
            isPlaying = false
            mediaPlayer?.start()
        }
        else{
            binding.songMiniplayerIv.setImageResource(R.drawable.btn_miniplayer_play)
            isPlaying = true
            if(mediaPlayer?.isPlaying == true){ //미디어플레이어 재생 중 아닐 때 pause하면 오류날 수 있어서 방지하기 위함
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
//        timer = Timer(song.playTime, song.isPlaying)
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
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