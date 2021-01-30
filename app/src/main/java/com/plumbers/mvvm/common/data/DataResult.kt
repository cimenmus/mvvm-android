package com.plumbers.mvvm.common.data

import com.plumbers.mvvm.common.AppError

sealed class DataResult<out R> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val appError: AppError) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$appError]"
            Loading -> "Loading"
        }
    }

    val resultData: R?
        get() = if (this is Success) {
            data
        } else {
            null
        }
}

val DataResult<*>.succeeded
    get() = this is DataResult.Success && data != null
