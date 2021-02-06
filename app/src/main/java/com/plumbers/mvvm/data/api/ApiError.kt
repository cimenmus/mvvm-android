package com.plumbers.mvvm.data.api

import com.google.gson.annotations.SerializedName

data class ApiError(

    @SerializedName("poster_path")
    var success: Boolean? = false,

    @SerializedName("status_code")
    val code: Int? = 0,

    @SerializedName("status_message")
    val message: String? = "Unknown API error"
)
