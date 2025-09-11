package com.bimabk.common.service

import com.bimabk.data.response.UserDetailResponse
import com.bimabk.data.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getListUser(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): List<UsersResponse.UsersResponseItem>

    @GET("users/{id}")
    suspend fun getUserDetail(@Path("id") id: String): UserDetailResponse
}