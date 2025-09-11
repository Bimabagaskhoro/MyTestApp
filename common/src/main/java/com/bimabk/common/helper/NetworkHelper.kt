package com.bimabk.common.helper

import com.bimabk.common.helper.Constant.ERROR_DATA
import com.bimabk.common.helper.Constant.ERROR_EMPTY
import retrofit2.HttpException

internal suspend fun <T> processResponse(action: suspend () -> T?): T {
    return try {
        action() ?: throw Exception(ERROR_EMPTY, Exception(ERROR_DATA))
    } catch (error: HttpException) {
        val errorMessage = error.message ?: error.message()
        throw Exception("${error.code()}", Exception(errorMessage))
    }
}