package com.plumbers.mvvm.di.module

import com.plumbers.mvvm.MainActivity
import com.plumbers.mvvm.di.annotation.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @MainScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity
}