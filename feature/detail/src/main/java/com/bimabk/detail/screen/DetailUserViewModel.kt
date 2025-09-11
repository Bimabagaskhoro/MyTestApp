package com.bimabk.detail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimabk.common.helper.UiState
import com.bimabk.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn

class DetailUserViewModel(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userId = savedStateHandle.get<String>("userId") ?: ""

    val userDetail = callbackFlow {
        send(UiState.Loading)
        try {
            val data = userRepository.getUserDetail(userId.toInt())
            send(UiState.Success(data))
        } catch (e: Throwable) {
            send(UiState.Error(e))
        } finally {
            close()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Init)
}