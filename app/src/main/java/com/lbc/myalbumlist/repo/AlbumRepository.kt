package com.lbc.myalbumlist.repo

import com.lbc.myalbumlist.repo.data.AlbumDataSource
import com.lbc.myalbumlist.repo.data.model.AlbumEntity
import com.lbc.myalbumlist.repo.ws.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumRepository(
    private val apiService: ApiService,
    private val albumDataSource: AlbumDataSource
    ) {

    suspend fun getAlbums(): Flow<List<AlbumEntity>> =
        withContext(Dispatchers.IO) { albumDataSource.getAlbums() }

    suspend fun updateAlbums() =
        flow {
            val response = apiService.getAlbums()
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.IO) {
                    albumDataSource.insertAll(response.body()!!)
                }
            }
            emit(response.isSuccessful)
        }
}