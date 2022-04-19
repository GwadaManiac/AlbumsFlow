package com.lbc.myalbumlist.repo.data

import android.content.Context
import com.lbc.myalbumlist.model.Album
import com.lbc.myalbumlist.repo.data.room.AlbumDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomAlbumDataSource(context: Context): AlbumDataSource {

    private val albumDao = AlbumDatabase.getDatabase(context).albumDao()

    override fun getAlbums(): Flow<List<Album>> =
        albumDao.getAlbums().map { list-> list.map { it.toAlbumModel() } }

    override suspend fun insertAll(albumList: List<Album>) {
        albumDao.insertAllReplace(albumList.map { it.toAlbumEntity() })
    }
}