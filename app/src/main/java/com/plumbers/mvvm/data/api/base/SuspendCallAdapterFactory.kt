package com.plumbers.mvvm.data.api.base

import com.plumbers.mvvm.data.result.Result
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

// https://stackoverflow.com/questions/56483235/how-to-create-a-call-adapter-for-suspending-functions-in-retrofit/57816819#57816819
// https://stackoverflow.com/questions/56483235/how-to-create-a-call-adapter-for-suspending-functions-in-retrofit
class SuspendCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}
