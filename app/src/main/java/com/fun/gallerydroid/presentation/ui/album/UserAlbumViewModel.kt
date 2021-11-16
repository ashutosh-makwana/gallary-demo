package com.`fun`.gallerydroid.presentation.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`fun`.gallerydroid.common.Resource
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserAlbumViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableLiveData<UserPostsUiState> =
        MutableLiveData()

    // The UI collects from this StateFlow to get its state updates
    val uiState: LiveData<UserPostsUiState> = _uiState

    // Represents different states for the user posts list screen
    sealed class UserPostsUiState {
        object Loading : UserPostsUiState()
        data class Success(val posts: List<AlbumDto>) : UserPostsUiState()
        data class Error(val exception: String) : UserPostsUiState()
    }

    fun getPosts(id:Int) {
        getAlbumsUseCase.getAlbumPhotos(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        _uiState.value = UserPostsUiState.Success(it)
                    }
                }
                is Resource.Error -> {
                    _uiState.value =
                        UserPostsUiState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _uiState.value = UserPostsUiState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

}