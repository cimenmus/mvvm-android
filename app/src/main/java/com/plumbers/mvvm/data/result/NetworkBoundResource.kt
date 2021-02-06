package com.plumbers.mvvm.data.result

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
