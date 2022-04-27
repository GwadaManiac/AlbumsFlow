package com.lbc.myalbumlist.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.lbc.myalbumlist.model.Album
import com.lbc.myalbumlist.repo.AlbumRepository
import com.lbc.myalbumlist.repo.data.AlbumDataSource
import com.lbc.myalbumlist.repo.data.RoomAlbumDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumViewModelTest {

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("ViewModel thread")

    private lateinit var viewModel: AlbumViewModel
    private lateinit var repository: AlbumRepository
    private lateinit var dataSource: AlbumDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        val context = ApplicationProvider.getApplicationContext<Context>()
        dataSource = RoomAlbumDataSource(context)
        repository = AlbumRepository(dataSource)
        viewModel = AlbumViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetAlbumsSuccess() = runTest(StandardTestDispatcher()) {
        val album = Album(1,1,"My Album", "https://picsum.photos/200/300", "https://picsum.photos/200")
        launch(Dispatchers.IO) {
            dataSource.insertAll(listOf(album))
        }
        viewModel.getAlbums()
        Assert.assertTrue(viewModel.albumFlow.first().contains(album.toAlbumEntity()))
    }
}