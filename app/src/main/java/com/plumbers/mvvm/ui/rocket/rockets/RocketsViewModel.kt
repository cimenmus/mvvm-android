package com.plumbers.mvvm.ui.rocket.rockets

import androidx.lifecycle.*
import com.plumbers.mvvm.data.model.RocketModel
import com.plumbers.mvvm.data.repository.rocket.RocketRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class RocketsViewModel
@Inject constructor(private val rocketRepository: RocketRepository): ViewModel() {

    val rocketsLiveData = MutableLiveData<List<RocketModel>>()

    fun getRockets() =
        viewModelScope.launch {
            rocketRepository
                .getRockets()
                .onStart { /* emit loading state */ }
                .catch { exception -> /* emit error state */ }
                .collect {
                    rocketsLiveData.postValue(it)
                }
        }
}