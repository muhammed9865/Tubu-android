package com.example.tubu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tubu.data.local.converters.PlaylistConverter
import com.example.tubu.data.local.dao.PlaylistsDao
import com.example.tubu.data.model.playlists.Playlist

@Database(
    entities = [Playlist::class],
    version = 1
)
@TypeConverters(
    PlaylistConverter::class
)
abstract class CachedDatabase:RoomDatabase() {
    abstract fun getPlaylistDao():PlaylistsDao

    companion object {
        val lock = Any()
        private var instance: CachedDatabase? = null
        fun getInstance(context: Context): CachedDatabase? {
            if (instance == null) {
                synchronized(lock) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            CachedDatabase::class.java,
                            "subjectDb.db"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}