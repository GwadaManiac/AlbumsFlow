package com.lbc.myalbumlist.repo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lbc.myalbumlist.repo.data.model.AlbumEntity

@Database(version = 1, entities = [AlbumEntity::class])
abstract class AlbumDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {

        @Volatile
        private var INSTANCE: AlbumDatabase? = null

        fun getDatabase(context: Context): AlbumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlbumDatabase::class.java,
                    AlbumDatabase::class.java.simpleName
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}