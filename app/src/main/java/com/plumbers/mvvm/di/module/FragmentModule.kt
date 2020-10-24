package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.di.annotation.FragmentScope
import com.plumbers.mvvm.ui.rocket.rockets.RocketsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeRocketsFragment(): RocketsFragment

}