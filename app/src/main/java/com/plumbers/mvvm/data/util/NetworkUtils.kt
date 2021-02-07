package com.plumbers.mvvm.data.util

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Suppress("DEPRECATION")
class NetworkUtils @Inject constructor(@ApplicationContext private val context: Context) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }
}