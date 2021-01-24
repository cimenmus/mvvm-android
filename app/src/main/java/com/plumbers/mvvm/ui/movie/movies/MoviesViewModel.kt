package com.plumbers.mvvm.ui.movie.movies

import androidx.lifecycle.*
import com.plumbers.mvvm.data.model.MovieModel
import com.plumbers.mvvm.data.repository.movie.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    val moviesLiveData = MutableLiveData<List<MovieModel>>()

    /*
    fun getMovies() =
        viewModelScope.launch {
            movieRepository
                .getPopularMovies(page = 1)
                .onStart { /* emit loading state */ }
                .catch { exception -> /* emit error state */ }
                .collect {
                    moviesLiveData.postValue(it)
                }
        }
    */

    fun getMovies(){}
}