package com.bimabk.data.pagging


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bimabk.common.service.UserService
import com.bimabk.data.mapper.mapToUiModelMediator
import com.bimabk.data.room.RemoteKeysDao
import com.bimabk.data.room.UserDao
import com.bimabk.data.room.model.RemoteKeys
import com.bimabk.data.uimodel.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val usersDao: UserDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val apiService: UserService,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteMediator<Int, UserUiModel>() {

    companion object {
        const val PAGE_SIZE = 4
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyForFirstItem(): RemoteKeys? {
        return withContext(ioDispatcher) {
            remoteKeysDao.getRemoteKeys().firstOrNull()
        }
    }

    private suspend fun getRemoteKeyForLastItem(): RemoteKeys? {
        return withContext(ioDispatcher) {
            val keys = remoteKeysDao.getRemoteKeys()
            val lastKey = keys.lastOrNull()
            lastKey
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserUiModel>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                1
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem()
                val nextKey = remoteKeys?.nextKey

                nextKey ?: run {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        return withContext(ioDispatcher) {
            try {
                val users = apiService.getListUser(page = page, limit = PAGE_SIZE)
                val endOfPagination = users.isEmpty() || users.size < PAGE_SIZE
                if (users.isNotEmpty()) {
                    if (loadType == LoadType.REFRESH) {
                        remoteKeysDao.deleteRemoteKeys()
                        usersDao.deleteAllUsers()
                    }

                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPagination) null else page + 1

                    val remoteKeys = users.map {
                        RemoteKeys(
                            id = it.id ?: 0,
                            prevKey = prevKey,
                            currentPage = page,
                            nextKey = nextKey
                        )
                    }
                    remoteKeysDao.insertAll(remoteKeys)

                    val uiModels = mapToUiModelMediator(users, page)

                    usersDao.insertUser(uiModels)
                }

                val result = MediatorResult.Success(endOfPaginationReached = endOfPagination)
                result

            } catch (error: IOException) {
                MediatorResult.Error(error)
            } catch (error: HttpException) {
                MediatorResult.Error(error)
            } catch (error: Exception) {
                MediatorResult.Error(error)
            }
        }
    }

}