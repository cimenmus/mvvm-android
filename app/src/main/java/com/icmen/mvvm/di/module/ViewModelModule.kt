package com.icmen.mvvm.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.icmen.mvvm.di.ViewModelFactory
import com.icmen.mvvm.di.annotation.ViewModelKey
import com.icmen.mvvm.ui.MainViewModel
import com.icmen.mvvm.ui.rocket.rockets.RocketsViewModel
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
}