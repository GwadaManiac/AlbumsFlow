package com.lbc.myalbumlist.repo.data

import com.lbc.myalbumlist.model.Album
import com.lbc.myalbumlist.repo.data.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface AlbumDataSource {

    suspend fun getAlbums(): Flow<List<AlbumEntity>>
    suspend fun insertAll(albumList: List<Album>)
}