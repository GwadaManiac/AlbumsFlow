package com.lbc.myalbumlist.repo.ws

import com.lbc.myalbumlist.model.Album
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): Flow<List<Album>>
}