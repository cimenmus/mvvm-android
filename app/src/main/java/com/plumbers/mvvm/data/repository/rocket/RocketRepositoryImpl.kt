package com.plumbers.mvvm.data.repository.rocket

import com.plumbers.mvvm.common.util.NetworkUtils
import com.plumbers.mvvm.data.model.RocketModel
import com.plumbers.mvvm.data.source.rocket.RocketDataSource
import javax.inject.Inject
import javax.inject.Named

class RocketRepositoryImpl
@Inject constructor(private val networkUtils: NetworkUtils,
                    @Named("remote")private val rocketRemoteDataSource: RocketDataSource,
                    @Named("local")private val rocketLocalDataSource: RocketDataSource): RocketRepository {

    override suspend fun getRockets(): List<RocketModel> {
        return when(networkUtils.isNetworkAvailable()){
            true -> {
                val rockets = rocketRemoteDataSource.getRockets()
                saveRockets(rockets = rockets)
                rockets
            }
            else -> rocketLocalDataSource.getRockets()
        }
    }

    override suspend fun saveRockets(rockets: List<RocketModel>) =
        rocketLocalDataSource.saveRockets(rockets = rockets)
}