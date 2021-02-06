package com.plumbers.mvvm.data.api.base

import com.google.gson.Gson
import com.plumbers.mvvm.ErrorType
import com.plumbers.mvvm.common.ApiError
import com.plumbers.mvvm.common.AppError
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

// https://stackoverflow.com/questions/56483235/how-to-create-a-call-adapter-for-suspending-functions-in-retrofit/57816819#57816819
// https://stackoverflow.com/questions/56483235/how-to-create-a-call-adapter-for-suspending-functions-in-retrofit
class ResultCall<T : BaseApiResponse>(proxy: Call<T>, private val defaultValue: Any) :
    CallDelegate<T, T>(proxy) {
    private fun onResponse(callback: Callback<T>, response: Response<T>) {
        val code = response.code()
        var body = response.body()
        if (body == null) {
            body = (defaultValue as? T?)
        }
        val result = if (code in 200 until 300) {
            body?.statusCode = response.code()
            body?.message = response.message()
        } else {
            val appError = getAppError(response)
            body?.message = appError.message
            body?.statusCode = appError.code
        }
        callback.onResponse(this@ResultCall, Response.success(body))
    }

    private fun onFailure(callback: Callback<T>, t: Throwable) {
        val body = (defaultValue as? T?)
        if (t is IOException) {
            body?.statusCode = 500
            body?.message = "Network Error: ${t.message}"
        } else {
            body?.statusCode = 500
            body?.message = "Other Failure: ${t.message}"
        }

        callback.onResponse(this@ResultCall, Response.success(body))
    }

    override fun enqueueImpl(callback: Callback<T>) = proxy.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) =
            onResponse(callback, response)

        override fun onFailure(call: Call<T>, t: Throwable) = onFailure(callback, t)
    })

    override fun cloneImpl() = ResultCall(proxy.clone(), defaultValue)
    override fun timeout(): Timeout {
        return Timeout.NONE
    }

    private fun getAppError(response: Response<T>): AppError {
        response.errorBody()?.let {
            return try {
                val apiError = Gson().fromJson(it.string(), ApiError::class.java)
                AppError(
                    type = ErrorType.HTTP,
                    code = apiError.code ?: 0,
                    message = apiError.message ?: "Unknown API error."
                )
            } catch (exception: Exception) {
                AppError(type = ErrorType.HTTP, message = "Unknown API error.")
            }
        } ?: run {
            return AppError(type = ErrorType.HTTP, message = "Unknown API error.")
        }
    }
}
