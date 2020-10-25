package com.plumbers.mvvm.data.source.rocket

import com.plumbers.mvvm.data.model.RocketModel
import javax.inject.Inject

class RocketLocalDataSource @Inject constructor(): RocketDataSource {

    override suspend fun getRockets(): List<RocketModel> {
        return listOf(
            RocketModel(id = 10),
            RocketModel(id = 20),
            RocketModel(id = 30),
            RocketModel(id = 40),
            RocketModel(id = 50),
        )
    }

    override suspend fun saveRockets(rockets: List<RocketModel>) {
        print("rockets saved!")
    }
}