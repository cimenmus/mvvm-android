package com.icmen.mvvm

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.icmen.mvvm.di.AppInjector
import com.icmen.mvvm.di.component.AppComponent
import dagger.android.*
import javax.inject.Inject

class App: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}