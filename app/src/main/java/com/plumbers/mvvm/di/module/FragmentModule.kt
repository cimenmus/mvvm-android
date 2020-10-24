package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.di.annotation.FragmentScope
import com.plumbers.mvvm.ui.mission.missiondetails.MissionDetailsFragment
import com.plumbers.mvvm.ui.mission.missions.MissionsFragment
import com.plumbers.mvvm.ui.rocket.rocketdetail.RocketDetailsFragment
import com.plumbers.mvvm.ui.rocket.rockets.RocketsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeRocketsFragment(): RocketsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeRocketDetailsFragment(): RocketDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMissionsFragment(): MissionsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMissionDetailsFragment(): MissionDetailsFragment

}