package com.plumbers.mvvm.ui.rocket.rockets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plumbers.mvvm.data.model.RocketModel
import com.plumbers.mvvm.data.repository.rocket.RocketRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RocketsViewModel
@Inject constructor(private val rocketRepository: RocketRepository): ViewModel() {

    val rocketsLiveData = MutableLiveData<List<RocketModel>>()

    fun getRockets() =
        viewModelScope.launch {
            rocketsLiveData.postValue(rocketRepository.getRockets())
        }
}