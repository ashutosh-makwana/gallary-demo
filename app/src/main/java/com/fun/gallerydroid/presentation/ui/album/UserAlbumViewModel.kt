package com.`fun`.gallerydroid.presentation.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`fun`.gallerydroid.common.Resource
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.data.remote.dto.PhotoDto
import com.`fun`.gallerydroid.domain.usecase.GetAlbumsUseCase
import com.`fun`.gallerydroid.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserAlbumViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableLiveData<UserAlbumsUiState> =
        MutableLiveData()

    // The UI collects from this StateFlow to get its state updates
    val uiState: LiveData<UserAlbumsUiState> = _uiState

    // Represents different states for the user posts list screen
    sealed class UserAlbumsUiState {
        object Loading : UserAlbumsUiState()
        data class Success(val posts: List<AlbumDto>) : UserAlbumsUiState()
        data class Error(val exception: String) : UserAlbumsUiState()
    }

    // Backing property to avoid state updates from other classes
    private val _uiPhotosState: MutableLiveData<UserPhotosUiState> =
        MutableLiveData()

    // The UI collects from this StateFlow to get its state updates
    val uiPhotosState: LiveData<UserPhotosUiState> = _uiPhotosState

    // Represents different states for the user posts list screen
    sealed class UserPhotosUiState {
        object Loading : UserPhotosUiState()
        data class Success(val photos: Pair<List<PhotoDto>, Int>) : UserPhotosUiState()
        data class Error(val exception: String) : UserPhotosUiState()
    }

    fun getAlbums(id: Int) {
        getAlbumsUseCase.getAlbums(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        _uiState.value = UserAlbumsUiState.Success(it)
                    }
                }
                is Resource.Error -> {
                    _uiState.value =
                        UserAlbumsUiState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _uiState.value = UserAlbumsUiState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAlbumPhotos(id: Int, position: Int) {
        getPhotosUseCase.getAlbumPhotos(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        _uiPhotosState.value = UserPhotosUiState.Success(Pair(it, position))
                    }
                }
                is Resource.Error -> {
                    _uiPhotosState.value =
                        UserPhotosUiState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _uiPhotosState.value = UserPhotosUiState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

}