package com.plumbers.mvvm.data

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.data.data
import com.plumbers.mvvm.common.data.succeeded

abstract class NetworkBoundResource<ResultType> {

    abstract fun isOnline(): Boolean

    open fun shouldFetch(): Boolean = true

    abstract suspend fun loadFromNetwork(): Result<ResultType>

    abstract suspend fun loadFromDb(): Result<ResultType>

    abstract suspend fun saveNetworkResult(data: ResultType)

    suspend fun execute(): Result<ResultType> {
        if (shouldFetch().not() || isOnline().not()){
            return loadFromDb()
        }
        loadFromNetwork().apply {
            if (succeeded) {
                saveNetworkResult(data = data!!)
            }
            return this
        }
    }
}
