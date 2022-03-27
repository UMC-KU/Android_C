package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.flo.databinding.FragmentAlbumBinding
<<<<<<< HEAD
import com.google.android.material.tabs.TabLayoutMediator
=======
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa

class AlbumFragment : Fragment() {

    lateinit var binding : FragmentAlbumBinding

<<<<<<< HEAD
    private val information = arrayListOf("수록곡","상세정보","영상")

=======
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlbumBinding.inflate(inflater,container,false)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
        }

<<<<<<< HEAD
        val albumAdapter=AlbumVPAdapter(this)
        binding.albumContentVp.adapter=albumAdapter

        TabLayoutMediator(binding.albumContentTb,binding.albumContentVp){
            tab,position->
            tab.text=information[position]
        }.attach()

=======
>>>>>>> 297157fda6b0e61735297c01b9fc13cea11fa3aa
        return binding.root
    }

}