package com.lbc.myalbumlist.repo.data

import androidx.room.Dao
import com.lbc.myalbumlist.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDataSource {

    fun getAlbums(): Flow<List<Album>>

    suspend fun insertAll(albumList: List<Album>)
}