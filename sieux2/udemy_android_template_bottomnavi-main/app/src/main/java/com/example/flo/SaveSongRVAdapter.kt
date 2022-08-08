package com.example.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.entities.Song
import com.example.flo.databinding.ItemLockerSavesongAlbumBinding

class SaveSongRVAdapter(private var saveSongList: ArrayList<Song>): RecyclerView.Adapter<SaveSongRVAdapter.ViewHolder>() {

    private val songs = ArrayList<Song>()
    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveSongRVAdapter.ViewHolder {
        val binding: ItemLockerSavesongAlbumBinding = ItemLockerSavesongAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemAlbumMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }

    }

    override fun getItemCount(): Int {
        return songs.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }


    inner class  ViewHolder(val binding: ItemLockerSavesongAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(savesong: Song) {
            binding.itemAlbumTitleTv.text = savesong.title
            binding.itemAlbumSingerTv.text = savesong.singer
            binding.itemAlbumImgIv.setImageResource(savesong.coverImg!!)

        }

    }

}