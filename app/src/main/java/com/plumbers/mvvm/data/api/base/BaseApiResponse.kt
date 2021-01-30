package com.plumbers.mvvm.data.api.base

import com.google.gson.Gson
import com.plumbers.mvvm.data.api.ResultState

open class BaseApiResponse(
    @Transient open var statusCode: Int = 0,
    @Transient open var message: String? = null
) {

    fun toJson(): String {
        return Gson().toJson(this)
    }

    fun isSuccess(): Boolean {
        return statusCode in 200..299
    }

    fun getResultState(): ResultState {
        if (statusCode in 200..299) {
            return ResultState.SUCCESS
        }
        return ResultState.FAIL
    }

    fun getErrorMessage(): String {
        val className = this::class
        return "${className.simpleName} failed! -> [${this.statusCode}] {${this.message}"
    }
}
