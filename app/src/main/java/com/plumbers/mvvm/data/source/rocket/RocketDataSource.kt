package com.plumbers.mvvm.data.source.rocket

import com.plumbers.mvvm.data.model.RocketModel

interface RocketDataSource {

    suspend fun getRockets(): List<RocketModel>

    suspend fun saveRockets(rockets: List<RocketModel>)
}