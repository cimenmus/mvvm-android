package com.plumbers.mvvm.common

import com.plumbers.mvvm.ErrorType

data class AppError(
    var code: Int = 0,
    val message: String = "",
    val type: ErrorType = ErrorType.UNDEFINED
)
