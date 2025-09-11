package com.bimabk.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bimabk.data.uimodel.UserUiModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserUiModel>)

    @Query("SELECT * FROM user ORDER BY page ASC, userId ASC")
    fun getUsers(): PagingSource<Int, UserUiModel>

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}