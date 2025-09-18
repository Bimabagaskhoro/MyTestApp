package com.bimabk.mytestapp.di

import androidx.room.Room
import com.bimabk.common.service.UserService
import com.bimabk.dashboard.DashboardViewModel
import com.bimabk.dashboard.history.HistoryViewModel
import com.bimabk.data.pagging.RemoteMediator
import com.bimabk.data.repository.UserRepository
import com.bimabk.data.repository.UserRepositoryImpl
import com.bimabk.data.room.RemoteKeysDao
import com.bimabk.data.room.UserDao
import com.bimabk.data.room.UsersDatabase
import com.bimabk.detail.screen.DetailUserViewModel
import com.bimabk.list.ListUsersViewModel
import com.bimabk.mytestapp.RouteToImpl
import com.bimabk.router.RouteTo
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun appModule() = listOf(
    serviceModule,
    databaseModule,
    repositoryModule,
    viewModelModule,
    commonModule
)

val serviceModule = module {
    single<UserService> { com.bimabk.common.helper.provideApi<UserService>(androidContext()) }
}

val databaseModule = module {
    single<UsersDatabase> {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            "users_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<UserDao> { get<UsersDatabase>().userDao() }
    single<RemoteKeysDao> { get<UsersDatabase>().remoteKeysDao() }

    single<RemoteMediator> {
        RemoteMediator(
            usersDao = get(),
            remoteKeysDao = get(),
            apiService = get(),
            ioDispatcher = get()
        )
    }
}

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            userService = lazy { get() },
            ioDispatcher = get(),
            userDao = get(),
            remoteMediator = get()
        )
    }
}

val viewModelModule = module {
    viewModelOf(::ListUsersViewModel)
    viewModelOf(::DetailUserViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::HistoryViewModel)
}

val commonModule = module {
    single { Dispatchers.IO }
    single<RouteTo> { RouteToImpl() }
}