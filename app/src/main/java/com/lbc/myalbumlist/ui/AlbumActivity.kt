package com.lbc.myalbumlist.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lbc.myalbumlist.R
import com.lbc.myalbumlist.databinding.ActivityAlbumsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumActivity: AppCompatActivity() {

    private val albumViewModel by viewModel<AlbumViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityAlbumsBinding.inflate(layoutInflater).apply {
            setContentView(root)

            val albumAdapter = AlbumAdapter()
            albumRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@AlbumActivity,
                    LinearLayoutManager.VERTICAL, false)
                adapter = albumAdapter
            }
            lifecycleScope.launchWhenResumed {
                albumViewModel.uiState.observe(this@AlbumActivity) {
                    when (it) {
                        AlbumViewModel.AlbumUpdateState.Updating -> {
                            albumRecyclerView.isVisible = false
                            noAlbumMessage.isVisible = false
                            progressLoader.isVisible = true
                        }
                        AlbumViewModel.AlbumUpdateState.Success -> {
                            albumRecyclerView.isVisible = albumViewModel.albumFlow.value.isNotEmpty()
                            noAlbumMessage.isVisible = albumViewModel.albumFlow.value.isEmpty()
                            progressLoader.isVisible = false
                            Toast.makeText(this@AlbumActivity,
                                R.string.update_albums_success, Toast.LENGTH_SHORT).show()
                        }
                        AlbumViewModel.AlbumUpdateState.Error -> {
                            albumRecyclerView.isVisible = albumViewModel.albumFlow.value.isNotEmpty()
                            noAlbumMessage.isVisible = albumViewModel.albumFlow.value.isEmpty()
                            progressLoader.isVisible = false
                            Toast.makeText(this@AlbumActivity,
                                R.string.update_albums_error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                albumViewModel.albumFlow.collect {
                    progressLoader.isVisible = false
                    albumRecyclerView.isVisible = true
                    noAlbumMessage.isVisible = false
                    albumAdapter.submitList(it)
                }
            }
            lifecycle.coroutineScope.launch {
                albumViewModel.getAlbums()
            }
            albumViewModel.updateAlbums()
        }

    }
}