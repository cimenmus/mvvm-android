package com.plumbers.mvvm.data.source.rocket

import com.plumbers.mvvm.data.model.RocketModel
import kotlinx.coroutines.flow.Flow

interface RocketDataSource {

    suspend fun getRockets(): Flow<List<RocketModel>>

    suspend fun saveRockets(rockets: List<RocketModel>)
}