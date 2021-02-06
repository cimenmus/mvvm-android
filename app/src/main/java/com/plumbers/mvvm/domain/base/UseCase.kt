package com.plumbers.mvvm.domain.base

import com.plumbers.mvvm.data.ErrorType
import com.plumbers.mvvm.data.AppError
import com.plumbers.mvvm.data.result.Result

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class UseCase<in P, R>() {

    /** Executes the use case synchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    operator fun invoke(parameters: P): Result<R> {
        return try {
            execute(parameters).let { Result.Success(it) }
        } catch (e: Exception) {
            val error = AppError(type = ErrorType.USECASE, message = e.localizedMessage ?: "")
            Result.Error(error)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}
