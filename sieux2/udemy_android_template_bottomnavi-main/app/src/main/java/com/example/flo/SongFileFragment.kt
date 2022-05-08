package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSearchBinding
import com.example.flo.databinding.FregmentSavesongBinding
import com.example.flo.databinding.FregmentSongfileBinding

class SongFileFragment : Fragment() {

    lateinit var binding: FregmentSongfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FregmentSongfileBinding.inflate(inflater,container,false)
        return binding.root

    }
}