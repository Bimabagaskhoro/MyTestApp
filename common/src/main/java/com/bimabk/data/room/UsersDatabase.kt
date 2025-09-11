package com.bimabk.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bimabk.data.room.model.RemoteKeys
import com.bimabk.data.room.utils.Converters
import com.bimabk.data.uimodel.UserUiModel

@Database(entities = [UserUiModel::class, RemoteKeys::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}