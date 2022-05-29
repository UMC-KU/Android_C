package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flo.data.entities.Album
import com.example.flo.data.entities.Like
import com.example.flo.data.entities.Song
import com.example.flo.data.entities.User


@Database(entities = [Song::class, User::class, Like::class, Album::class], version = 1)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun userDao(): UserDao
    abstract fun albumDao(): AlbumDao

    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"//다른 데이터 베이스랑 이름겹치면 꼬임
                    ).allowMainThreadQueries().build() //원래 더 효율적으로 하려면 메인 스레드에 넘기지 않음
                }
            }

            return instance
        }
    }
}