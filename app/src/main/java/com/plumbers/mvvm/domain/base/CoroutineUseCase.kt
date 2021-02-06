package com.plumbers.mvvm.domain.base

import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.AppError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.plumbers.mvvm.data.result.Result

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let { Result.Success(it) }
            }
        } catch (e: Exception) {
            val error = AppError(type = ErrorType.USECASE, message = e.localizedMessage ?: "")
            Result.Error(error)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
