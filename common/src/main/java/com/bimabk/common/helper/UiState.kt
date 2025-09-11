package com.bimabk.common.helper

sealed interface UiState<out T> {
    object Init : UiState<Nothing>
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: Throwable? = null) : UiState<Nothing>
}
