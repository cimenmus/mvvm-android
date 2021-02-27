package com.plumbers.mvvm.domain.base

import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Executes business logic using Coroutines and returns a [Result].
 * BaseUseCase to make business logic for [Result] type returned data
 * The difference from [CoroutineUseCase] is:
 * return type of the [execute] method is [Result] is [ResultUseCase] whereas [execute] method inside [CoroutineUseCase] is not
 */
abstract class ResultUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /**
     * Executes the use case asynchronously and returns [Result].
     * @return [Result].
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) { execute(parameters) }
        } catch (e: Exception) {
            val error = AppError(type = ErrorType.USECASE, message = e.localizedMessage ?: "")
            Result.Error(error)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    abstract suspend fun execute(parameters: P): Result<R>
}
