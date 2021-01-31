package com.plumbers.mvvm.data.api.response

import com.google.gson.annotations.SerializedName
import com.plumbers.mvvm.data.api.base.BaseApiResponse

data class PersonDetailsApiResponse(
    val id: Int = 0,
    val name: String? = "",
    val biography: String? = "",
    @SerializedName("profile_path") val imagePath: String? = "",
) : BaseApiResponse()