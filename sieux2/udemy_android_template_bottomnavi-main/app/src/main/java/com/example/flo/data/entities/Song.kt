package com.example.flo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songTable")
data class Song(
    var title : String = "",
    var singer : String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var coverImg: Int? = null,
    var islike: Boolean = false

){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

