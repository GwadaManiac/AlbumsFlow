package com.lbc.myalbumlist.repo

import com.lbc.myalbumlist.repo.ws.ApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AlbumRepository() {

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

    suspend fun getAlbums() = apiService.getAlbums()
}