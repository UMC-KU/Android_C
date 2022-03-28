package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ActivitySongBinding
//import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding
    private var on = true


    fun setMIxStatus(){

        if(on){
            binding.songMixTg.setImageResource(R.drawable.btn_toggle_on)
            on = false
        }
        else{
            binding.songMixTg.setImageResource(R.drawable.btn_toggle_off)
            on = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)

        binding.songMixTg.setOnClickListener {
            setMIxStatus()
        }

        return binding.root
    }
}


