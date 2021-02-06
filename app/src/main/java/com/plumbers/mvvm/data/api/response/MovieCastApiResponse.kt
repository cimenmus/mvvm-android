package com.plumbers.mvvm.data.api.response

import com.plumbers.mvvm.data.model.MovieCastModel

data class MovieCastApiResponse(
    val id: Int = 0,
    val cast: List<MovieCastModel> = listOf(),
)
