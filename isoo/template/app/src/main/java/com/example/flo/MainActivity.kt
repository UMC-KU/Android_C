package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song : Song = Song()
    private var gSon : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        //val song = Song(binding.mainPlayerTitleTv.text.toString(), binding.mainPlayerSingerTv.text.toString(), 0, 60, false, "music_lilac")
        Log.d("Song", song.title + song.singer)

        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this,SongActivity::class.java))
/*            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("song", song)
            startActivity(intent)*/
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
        }

        initBottomNavigation()
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song:Song) {
        binding.mainPlayerTitleTv.text = song.title
        binding.mainPlayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime
    }

    override fun onStart() {
        super.onStart()
/*        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData",null)

        song = if(songJson == null) {
            Song("라일락","아이유(IU)",0,60,false,"music_lilac")
        } else {
            gSon.fromJson(songJson, Song::class.java)
        }*/
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!
        song = if(songId == 0) {
            songDB.songDao().getSong(1)
        } else {
            songDB.songDao().getSong(songId)
        }

        Log.d("song Id", song.id.toString())

        setMiniPlayer(song)
    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                240,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false
            )
        )
        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                1,
                240,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false
            )
        )
        songDB.songDao().insert(
            Song(
                "Bboom Bboom",
                "모모랜드 (MOMOLAND)",
                2,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false
            )
        )
        val _songs = songDB.songDao().getSongs()
        Log.d("inputDummySongs: ", _songs.toString())
    }
}