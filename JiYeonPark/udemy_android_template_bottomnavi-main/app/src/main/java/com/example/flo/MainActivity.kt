package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
import android.widget.TextView
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

<<<<<<< HEAD
        val song=Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString())

        binding.mainPlayerCl.setOnClickListener{
            //intent로 값 넘기기
//
            val intent = Intent(this,SongActivity::class.java)
//
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            startActivity(intent)

=======

        binding.mainPlayerCl.setOnClickListener{
            //startActivity(Intent(this,SongActivity::class.java))
            //intent로 값 넘기기
//            val title= R.id.main_player_cl_title.text.toString()
//            val singer=R.id.main_player_cl_singer.text.toString()

            val intent = Intent(this,SongActivity::class.java)
//            intent.putExtra("Title",title)
//            intent.putExtra("Singer",singer)
            startActivity(intent)
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
        }


        initBottomNavigation()

<<<<<<< HEAD
        Log.d("Song",song.title+song.singer)


=======
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
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
}