package com.plumbers.mvvm.data.source.rocket

import com.plumbers.mvvm.data.model.RocketModel
import javax.inject.Inject

class RocketRemoteDataSource @Inject constructor(): RocketDataSource{

    override suspend fun getRockets(): List<RocketModel> {
        return listOf(
            RocketModel(id = 1),
            RocketModel(id = 2),
            RocketModel(id = 3),
            RocketModel(id = 4),
            RocketModel(id = 5),
        )
    }

    override suspend fun saveRockets(rockets: List<RocketModel>) {
        TODO("Not yet implemented")
    }
}