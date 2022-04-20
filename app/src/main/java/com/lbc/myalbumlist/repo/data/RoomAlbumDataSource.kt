package com.lbc.myalbumlist.repo.data

import android.content.Context
import com.lbc.myalbumlist.model.Album
import com.lbc.myalbumlist.repo.data.model.AlbumEntity
import com.lbc.myalbumlist.repo.data.room.AlbumDatabase
import kotlinx.coroutines.flow.Flow

class RoomAlbumDataSource(context: Context): AlbumDataSource {

    private val albumDao = AlbumDatabase.getDatabase(context).albumDao()

    override suspend fun getAlbums(): Flow<List<AlbumEntity>> = albumDao.getAlbums()

    override suspend fun insertAll(albumList: List<Album>) {
        albumDao.insertAll(albumList.map { it.toAlbumEntity() })
    }
}