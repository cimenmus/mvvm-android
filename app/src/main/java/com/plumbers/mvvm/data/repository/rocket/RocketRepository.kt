package com.plumbers.mvvm.data.repository.rocket

import com.plumbers.mvvm.data.model.RocketModel
import kotlinx.coroutines.flow.Flow

interface RocketRepository {

    suspend fun getRockets(): Flow<List<RocketModel>>

    suspend fun saveRockets(rockets: List<RocketModel>)
}