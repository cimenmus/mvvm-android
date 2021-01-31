package com.plumbers.mvvm.ui.movie.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel
@ViewModelInject constructor(private val movieRepository: MovieRepository): ViewModel() {

    val movieCastLiveData = MutableLiveData<DataResult<List<MovieCastModel>>>()

    fun getCastOfTheMovie(movieId: Int) {
        movieCastLiveData.value = DataResult.Loading
        viewModelScope.launch {
            movieRepository.getCastOfAMovie(movieId = movieId).apply {
                movieCastLiveData.postValue(this)
            }
        }
    }
}