package com.plumbers.mvvm.data.api.response

import com.plumbers.mvvm.data.api.base.BaseApiResponse
import com.plumbers.mvvm.data.model.MovieModel

data class PopularMoviesApiResponse(
    val page: Int = 0,
    val results: List<MovieModel> = listOf(),
) : BaseApiResponse()
