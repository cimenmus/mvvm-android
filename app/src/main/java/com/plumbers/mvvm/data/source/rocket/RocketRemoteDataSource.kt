package com.plumbers.mvvm.data.source.rocket

import com.plumbers.mvvm.data.model.RocketModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RocketRemoteDataSource @Inject constructor(): RocketDataSource{

    override suspend fun getRockets(): Flow<List<RocketModel>> =
        flow {
            delay(1000)
            emit(listOf(
                RocketModel(id = 1),
                RocketModel(id = 2),
                RocketModel(id = 3),
                RocketModel(id = 4),
                RocketModel(id = 5),
            ))
        }

    override suspend fun saveRockets(rockets: List<RocketModel>) {
        TODO("Not yet implemented")
    }
}