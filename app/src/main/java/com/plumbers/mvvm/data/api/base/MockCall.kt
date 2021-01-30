package com.plumbers.mvvm.data.api.base

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okio.Timeout
import java.io.IOException

class MockCall(
    private val request: Request,
    private val responseStatus: ResponseStatus,
    private val response: Response
) : Call {

    enum class ResponseStatus {
        SUCCESS,
        ERROR,
        FAIL
    }

    override fun enqueue(responseCallback: Callback) {
        when (responseStatus) {
            ResponseStatus.FAIL ->
                responseCallback.onFailure(this, IOException("MockCall sample exception"))
            else ->
                responseCallback.onResponse(this, response)
        }
    }

    override fun isExecuted(): Boolean = false

    override fun timeout(): Timeout = Timeout.NONE

    override fun clone(): Call = this

    override fun isCanceled(): Boolean = false

    override fun cancel() {}

    override fun request(): Request? = request

    override fun execute(): Response = response
}
