package com.example.flo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemLockerSavesongAlbumBinding

class SaveSongRVAdapter(private var saveSongList: ArrayList<Song>): RecyclerView.Adapter<SaveSongRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveSongRVAdapter.ViewHolder {
        val binding: ItemLockerSavesongAlbumBinding = ItemLockerSavesongAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveSongRVAdapter.ViewHolder, position: Int) {
        holder.bind(saveSongList[position])

    }

    override fun getItemCount(): Int {
        return saveSongList.size
    }



    inner class  ViewHolder(val binding: ItemLockerSavesongAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(savesong: Song) {
            binding.itemAlbumTitleTv.text = savesong.title
            binding.itemAlbumSingerTv.text = savesong.singer
            binding.itemAlbumImgIv.setImageResource(savesong.coverImg!!)

        }

    }

}