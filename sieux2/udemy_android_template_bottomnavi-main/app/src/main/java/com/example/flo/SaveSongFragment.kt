package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.databinding.FragmentSearchBinding
import com.example.flo.databinding.FregmentSavesongBinding
import kotlin.collections.ArrayList

class SaveSongFragment : Fragment() {

    lateinit var binding: FregmentSavesongBinding
    private var savesongDatas = ArrayList<Song>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FregmentSavesongBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        savesongDatas.apply{
//            add(Song("Butter","방탄소년단 (BTS)",R.drawable.img_album_exp))
//            add(Song("Lilac","아이유 (iu)",R.drawable.img_album_exp2))
//            add(Song("Next Level","에스파 (AESPA)",R.drawable.img_album_exp3))
//            add(Song("Boy with Luv","방탄소년단 (BTS)",R.drawable.img_album_exp4))
//            add(Song("BBoom BBoom","모모랜드 (MOMOLAND)",R.drawable.img_album_exp5))
//            add(Song("Weekend","태연 (Tae Yeon)",R.drawable.img_album_exp6))
            add(Song("Butter","방탄소년단 (BTS)",0,0,false,"0",R.drawable.img_album_exp,false))
        }

        val saveSongRVAdapter = SaveSongRVAdapter(savesongDatas)
        binding.savesongSongListRv.adapter = saveSongRVAdapter
        binding.savesongSongListRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        Log.d("songin",savesongDatas.get(0).title)
    }



}