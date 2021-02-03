package com.plumbers.mvvm.domain

import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import com.plumbers.mvvm.di.qualifier.IoDispatcher
import com.plumbers.mvvm.domain.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class GetCastOfMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<MovieCastParameter, Result<List<MovieCastModel>>>(ioDispatcher) {

    override suspend fun execute(parameters: MovieCastParameter): Result<List<MovieCastModel>> =
        movieRepository.getCastOfMovie(movieId = parameters.movieId)
}

data class MovieCastParameter(val movieId: Int)