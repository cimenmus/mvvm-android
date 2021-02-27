package com.plumbers.mvvm.data.result

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType

/**
 * Loads data from Database, converts it to a [Result] and return
 */
abstract class DatabaseResource<ResultType> {

    /**
     * Override this to load data from database.
     * @return a [ResultType].
     */
    abstract suspend fun load(): ResultType?

    /**
     * Executes [load] asynchronously
     * @return a [Result].
     */
    suspend fun execute(): Result<ResultType> {
        load().apply {
            return when {
                this == null || (this is List<*> && this.isEmpty()) -> {
                    val appError = AppError(
                        type = ErrorType.DB_ITEM_NOT_FOUND,
                        message = "Can not found item on database"
                    )
                    Result.Error(appError)
                }
                else -> Result.Success(this)
            }
        }
    }
}
