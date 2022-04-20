package com.lbc.myalbumlist

import android.app.Application
import com.lbc.myalbumlist.repo.AlbumRepository
import com.lbc.myalbumlist.repo.data.RoomAlbumDataSource
import com.lbc.myalbumlist.ui.AlbumViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class AlbumApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AlbumApplication)
            modules(listOf(
                repositoriesModule,
                viewModelModule
            ))
        }
    }

    private val repositoriesModule = module {
        // Databases
        single { RoomAlbumDataSource(this@AlbumApplication) }

        // Repositories
        single { AlbumRepository(get() as RoomAlbumDataSource) }
    }

    private val viewModelModule = module {
        viewModel { AlbumViewModel(get()) }
    }
}