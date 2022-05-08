package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.*

@Entity(tableName = "SongTable")
data class Song (
    val title : String = "",
    val singer : String = "",
    var second : Int = 0,
    var playTime : Int = 0,
    var isPlaying : Boolean = false,
    var music : String = "",
    var coverImg: Int? = null,
    var isLike: Boolean = false
) : Serializable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
