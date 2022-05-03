package com.lbc.myalbumlist

import android.app.Application
import com.lbc.myalbumlist.repo.AlbumRepository
import com.lbc.myalbumlist.repo.data.RoomAlbumDataSource
import com.lbc.myalbumlist.repo.ws.ApiService
import com.lbc.myalbumlist.ui.AlbumViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                apiModule,
                repositoriesModule,
                viewModelModule
            ))
        }
    }

    private val apiModule = module {
        single {
            val retrofit = Retrofit.Builder()
            .baseUrl(LBC_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

            retrofit.create(ApiService::class.java)
        }
    }

    private val repositoriesModule = module {
        // Databases
        single { RoomAlbumDataSource(this@AlbumApplication) }

        // Repositories
        single { AlbumRepository(get(), get() as RoomAlbumDataSource) }
    }

    private val viewModelModule = module {
        viewModel { AlbumViewModel(get()) }
    }

    companion object {
        const val LBC_BASE_URL = "https://static.leboncoin.fr/"
    }
}