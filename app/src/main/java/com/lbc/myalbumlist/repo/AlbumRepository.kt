package com.lbc.myalbumlist.repo

import com.lbc.myalbumlist.model.Album
import com.lbc.myalbumlist.repo.data.AlbumDataSource
import com.lbc.myalbumlist.repo.ws.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AlbumRepository(private val albumDataSource: AlbumDataSource) {

    private val apiService: ApiService

    companion object {
        const val LBC_BASE_URL = "https://static.leboncoin.fr/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(LBC_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getAlbums(): Flow<List<Album>> {
        updateAlbums()
        return albumDataSource.getAlbums()
    }

    private suspend fun updateAlbums() {
        apiService.getAlbums().collect {
            withContext(Dispatchers.IO) {
                albumDataSource.insertAll(it)
            }
        }
    }
}