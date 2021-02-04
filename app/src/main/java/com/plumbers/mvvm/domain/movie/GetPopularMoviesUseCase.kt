package com.plumbers.mvvm.domain.movie

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import com.plumbers.mvvm.di.qualifier.IoDispatcher
import com.plumbers.mvvm.domain.base.ResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : ResultUseCase<PopularMoviesParameter, List<MovieModel>>(ioDispatcher) {

    override suspend fun execute(parameters: PopularMoviesParameter): Result<List<MovieModel>> =
        movieRepository.getPopularMovies(page = parameters.page)
}

data class PopularMoviesParameter(val page: Int)
