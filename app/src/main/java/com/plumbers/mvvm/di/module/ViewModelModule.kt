package com.plumbers.mvvm.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plumbers.mvvm.di.ViewModelFactory
import com.plumbers.mvvm.di.annotation.ViewModelKey
import com.plumbers.mvvm.ui.MainViewModel
import com.plumbers.mvvm.ui.mission.missiondetails.MissionDetailsViewModel
import com.plumbers.mvvm.ui.mission.missions.MissionsViewModel
import com.plumbers.mvvm.ui.rocket.rocketdetail.RocketDetailsViewModel
import com.plumbers.mvvm.ui.rocket.rockets.RocketsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RocketsViewModel::class)
    abstract fun bindRocketsViewModel(rocketsViewModel: RocketsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RocketDetailsViewModel::class)
    abstract fun bindRocketDetailsViewModel(rocketDetailsViewModel: RocketDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MissionsViewModel::class)
    abstract fun bindMissionsViewModel(missionsViewModel: MissionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MissionDetailsViewModel::class)
    abstract fun bindMissionDetailsViewModel(missionDetailsViewModel: MissionDetailsViewModel): ViewModel
}