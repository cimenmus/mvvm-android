package com.plumbers.mvvm.ui.movie.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.plumbers.mvvm.common.data.DataResult
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val moviesLiveData = MutableLiveData<DataResult<List<MovieModel>>>()
    var movieList = mutableListOf<MovieModel>()
    private var nextPage = 1

    fun getMovies() {
        moviesLiveData.value = DataResult.Loading
        viewModelScope.launch {
            movieRepository.getPopularMovies(page = nextPage).apply {
                if (this is DataResult.Success) {
                    movieList.addAll(data)
                    nextPage++
                }
                moviesLiveData.postValue(this)
            }
        }
    }
}
