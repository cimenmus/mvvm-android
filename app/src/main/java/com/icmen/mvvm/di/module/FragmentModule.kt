package com.icmen.mvvm.di.module

import com.icmen.mvvm.di.annotation.FragmentScope
import com.icmen.mvvm.ui.rocket.rockets.RocketsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeRocketsFragment(): RocketsFragment

}