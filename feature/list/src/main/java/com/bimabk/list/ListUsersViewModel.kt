package com.bimabk.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListUsersViewModel(
    private val userRepository: com.bimabk.data.repository.UserRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<com.bimabk.data.uimodel.UserUiModel>>(emptyList())
    val users: StateFlow<List<com.bimabk.data.uimodel.UserUiModel>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage

    private var currentPage = 1

    init {
        loadFirstPage()
    }

    private fun loadFirstPage() {
        currentPage = 1
        _users.value = emptyList()
        _isLastPage.value = false
        loadMoreUsers()
    }

    fun loadMoreUsers() {
        if (_isLoading.value || _isLastPage.value) {
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val newUsers = userRepository.getUsersPaginated(currentPage)
                if (newUsers.isEmpty() || newUsers.size < 4) {
                    _isLastPage.value = true
                }

                val currentUsers = _users.value.toMutableList()
                currentUsers.addAll(newUsers)
                _users.value = currentUsers

                currentPage++
            } catch (e: Exception) {
                println(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        loadFirstPage()
    }
}