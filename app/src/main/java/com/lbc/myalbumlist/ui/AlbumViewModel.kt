package com.lbc.myalbumlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbc.myalbumlist.repo.AlbumRepository
import com.lbc.myalbumlist.repo.data.model.AlbumEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumViewModel(private val albumRepository: AlbumRepository): ViewModel() {

    private val _uiState = MutableLiveData<AlbumUpdateState>()
    val uiState: LiveData<AlbumUpdateState> = _uiState

    private val _albumFlow = MutableStateFlow<List<AlbumEntity>>(emptyList())
    val albumFlow : StateFlow<List<AlbumEntity>> = _albumFlow

    suspend fun getAlbums() {
        viewModelScope.launch {
            albumRepository.getAlbums().collect {
                _albumFlow.emit(it)
            }
        }
    }

    fun updateAlbums() {
        viewModelScope.launch {
            _uiState.value = AlbumUpdateState.Updating
            albumRepository.updateAlbums().collect {
                _uiState.value = if (it) AlbumUpdateState.Success else AlbumUpdateState.Error
            }
        }
    }

    sealed class AlbumUpdateState {
        object Updating: AlbumUpdateState()
        object Success: AlbumUpdateState()
        object Error: AlbumUpdateState()
    }
}