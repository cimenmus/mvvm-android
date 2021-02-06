package com.plumbers.mvvm.data.api.base

import com.google.gson.Gson
import com.plumbers.mvvm.data.api.ResultState

open class BaseApiResponse(
    @Transient open var statusCode: Int = 0,
    @Transient open var message: String? = null
) {

    fun toJson(): String = Gson().toJson(this)

    fun isSuccess(): Boolean = statusCode in 200..299

    fun getResultState(): ResultState =
        if (statusCode in 200..299) {
            ResultState.SUCCESS
        } else {
            ResultState.FAIL
        }

    fun getErrorMessage(): String = "Error Code:$statusCode\n$message"
}
