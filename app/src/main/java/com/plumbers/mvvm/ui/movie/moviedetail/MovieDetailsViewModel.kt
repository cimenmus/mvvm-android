package com.plumbers.mvvm.ui.movie.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plumbers.mvvm.common.data.Result
import com.plumbers.mvvm.common.data.update
import com.plumbers.mvvm.data.model.MovieCastModel
import com.plumbers.mvvm.domain.movie.GetMovieCastUseCase
import com.plumbers.mvvm.domain.movie.MovieCastParameter
import kotlinx.coroutines.launch

class MovieDetailsViewModel
@ViewModelInject constructor(
    private val getMovieCastUseCase: GetMovieCastUseCase
) : ViewModel() {

    val movieCastLiveData = MutableLiveData<Result<List<MovieCastModel>>>()

    fun getCastOfTheMovie(movieId: Int) {
        movieCastLiveData.value = Result.Loading
        viewModelScope.launch {
            val params = MovieCastParameter(movieId = movieId)
            getMovieCastUseCase(parameters = params).update(liveData = movieCastLiveData)
        }
    }
}
