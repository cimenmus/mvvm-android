package com.plumbers.mvvm.data.source.rocket

import com.plumbers.mvvm.data.db.RocketDao
import com.plumbers.mvvm.data.model.RocketModel
import javax.inject.Inject

class RocketLocalDataSource
@Inject constructor(private val rocketDao: RocketDao): RocketDataSource {

    override suspend fun getRockets(): List<RocketModel> =
        rocketDao.getRockets()

    override suspend fun saveRockets(rockets: List<RocketModel>) {
        rocketDao.add(rockets = rockets)
    }
}