package com.lbc.myalbumlist.repo.ws

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.lbc.myalbumlist.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @Mock
    @MockResponse(body = "test.json")
    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): Response<List<Album>>
}