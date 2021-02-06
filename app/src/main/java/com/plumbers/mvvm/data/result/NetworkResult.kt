package com.plumbers.mvvm.data.result

import com.plumbers.mvvm.data.*

abstract class NetworkResult<ApiResponseType, ResultType> {

    abstract suspend fun load(): Result<ApiResponseType>

    abstract suspend fun getResult(apiResponse: ApiResponseType): ResultType?

    suspend fun execute(): Result<ResultType> {
        load().apply {
            return when {
                this.succeeded ->
                    getResult(apiResponse = data!!)?.let {
                        Result.Success(it)
                    } ?: run {
                        val error = AppError(type = ErrorType.UNEXPECTED_RESPONSE, message = "Unexpected API response")
                        Result.Error(appError = error)
                    }
                else -> this as Result.Error
            }
        }
    }
}
