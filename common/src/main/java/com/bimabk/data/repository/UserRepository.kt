package com.bimabk.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bimabk.common.helper.processResponse
import com.bimabk.common.service.UserService
import com.bimabk.data.mapper.mapToDetailUiModel
import com.bimabk.data.mapper.mapToUiModel
import com.bimabk.data.pagging.RemoteMediator
import com.bimabk.data.room.UserDao
import com.bimabk.data.uimodel.UserDetailUiModel
import com.bimabk.data.uimodel.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun getListUser(): List<UserUiModel>
    suspend fun getUserDetail(userId: Int): UserDetailUiModel
    fun getUsersPaging(): Flow<PagingData<UserUiModel>>
    suspend fun getUsersPaginated(page: Int): List<UserUiModel>
}

class UserRepositoryImpl(
    private val userService: Lazy<UserService>,
    private val ioDispatcher: CoroutineDispatcher,
    private val userDao: UserDao,
    private val remoteMediator: RemoteMediator
) : UserRepository {
    override suspend fun getListUser(): List<UserUiModel> =
        withContext(ioDispatcher) {
            val response = processResponse {
                userService.value.getListUser(page = 1, limit = 10)
            }
            mapToUiModel(response)
        }

    override suspend fun getUserDetail(userId: Int): UserDetailUiModel =
        withContext(ioDispatcher) {
            val response = processResponse {
                userService.value.getUserDetail(userId.toString())
            }
            mapToDetailUiModel(response)
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsersPaging(): Flow<PagingData<UserUiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = RemoteMediator.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 1,
                initialLoadSize = RemoteMediator.PAGE_SIZE,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                userDao.getUsers()
            }
        ).flow
    }

    override suspend fun getUsersPaginated(page: Int): List<UserUiModel> =
        withContext(ioDispatcher) {
            val response = processResponse {
                userService.value.getListUser(page = page, limit = 4)
            }
            mapToUiModel(response)
        }
}