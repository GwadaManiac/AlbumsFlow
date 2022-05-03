package com.lbc.myalbumlist.repo

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import co.infinum.retromock.Retromock
import com.lbc.myalbumlist.AlbumApplication
import com.lbc.myalbumlist.repo.data.RoomAlbumDataSource
import com.lbc.myalbumlist.repo.data.model.AlbumEntity
import com.lbc.myalbumlist.repo.ws.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class AlbumRepositoryTest {

    private lateinit var repository: AlbumRepository
    private lateinit var albums: List<AlbumEntity>

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val retrofit = Retrofit.Builder()
            .baseUrl(AlbumApplication.LBC_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mockRetrofit = Retromock.Builder()
            .defaultBodyFactory(ResourceBodyFactory())
            .retrofit(retrofit).build()
        val apiService = mockRetrofit.create(ApiService::class.java)
        repository = AlbumRepository(apiService, RoomAlbumDataSource(context))
        runBlocking {
            albums = repository.getAlbums().first()
        }
    }

    @Test
    fun testAlbumsCount() {
        Assert.assertTrue(albums.size == 25)
    }

    @Test
    fun testAlbumsItem3Title() {
        Assert.assertTrue(albums[2].title == "officia porro iure quia iusto qui ipsa ut modi")
    }
}