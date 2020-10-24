package com.plumbers.mvvm.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.plumbers.mvvm.App
import com.plumbers.mvvm.di.component.AppComponent
import com.plumbers.mvvm.di.component.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    fun init(app: App): AppComponent {

        val appComponent = DaggerAppComponent.builder().application(app).build()
        appComponent.inject(app)

        app.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {}

                override fun onActivityResumed(activity: Activity) {}

                override fun onActivityPaused(activity: Activity) {}

                override fun onActivityStopped(activity: Activity) {}

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

                override fun onActivityDestroyed(activity: Activity) {}
            }
        )
        return appComponent
    }

    private fun handleActivity(activity: Activity) {

        AndroidInjection.inject(activity)

        if (activity is FragmentActivity) {
            activity
                .supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object: FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(fm: FragmentManager,
                                                       f: Fragment,
                                                       savedInstanceState: Bundle?) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }
}