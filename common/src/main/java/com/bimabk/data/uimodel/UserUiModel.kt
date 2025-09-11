package com.bimabk.data.uimodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserUiModel(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    var page: Int
)
