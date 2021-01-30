package com.plumbers.mvvm.data.api.base

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CallAdapterFactory : CallAdapter.Factory() {
    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() =
            CallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType((callType as Class<*>).superclass)) {
                BaseApiResponse::class.java -> {
                    val defaultValue = (callType as Class<*>).newInstance()
                    ResultAdapter(
                        callType,
                        defaultValue
                    )
                }
                else -> null
            }
        }
        else -> null
    }
}
