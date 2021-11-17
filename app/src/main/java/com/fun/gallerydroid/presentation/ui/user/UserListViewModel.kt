package com.`fun`.gallerydroid.presentation.ui.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`fun`.gallerydroid.common.Constants
import com.`fun`.gallerydroid.common.Resource
import com.`fun`.gallerydroid.domain.model.User
import com.`fun`.gallerydroid.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableStateFlow<UserListUiState> =
        MutableStateFlow(UserListUiState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<UserListUiState> = _uiState

    // Represents different states for the user list screen
    sealed class UserListUiState {
        object Loading : UserListUiState()
        data class Success(val users: List<User>) : UserListUiState()
        data class Error(val exception: String) : UserListUiState()
    }

    init {
        getUsers()
    }

    private fun getUsers() {
        getUsersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        Log.e(Constants.TAG,"data view model ${it}")
                        _uiState.value = UserListUiState.Success(it)
                    }
                }
                is Resource.Error -> {
                    _uiState.value =
                        UserListUiState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _uiState.value = UserListUiState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}