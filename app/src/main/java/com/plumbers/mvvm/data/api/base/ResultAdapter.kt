package com.plumbers.mvvm.data.api.base

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultAdapter(
    private val type: Type,
    private val defaultValue: Any
) : CallAdapter<BaseApiResponse, Call<BaseApiResponse>> {
    override fun responseType() = type
    override fun adapt(call: Call<BaseApiResponse>): Call<BaseApiResponse> =
        ResultCall(call, defaultValue)
}
