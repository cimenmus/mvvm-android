package com.plumbers.mvvm.data.repository.rocket

import com.plumbers.mvvm.data.model.RocketModel

interface RocketRepository {

    suspend fun getRockets(): List<RocketModel>

    suspend fun saveRockets(rockets: List<RocketModel>)
}