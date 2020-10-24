package com.icmen.mvvm.di.module

import com.icmen.mvvm.MainActivity
import com.icmen.mvvm.di.annotation.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @MainScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity
}