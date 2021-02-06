package com.plumbers.mvvm.data

data class AppError(
    var code: Int = 0,
    val message: String = "",
    val type: ErrorType = ErrorType.UNDEFINED
)
