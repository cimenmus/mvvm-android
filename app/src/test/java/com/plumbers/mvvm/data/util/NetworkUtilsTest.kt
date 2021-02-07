package com.plumbers.mvvm.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.mockk.*
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

@Suppress("DEPRECATION")
class NetworkUtilsTest {

    private lateinit var context: Context
    private lateinit var networkUtils: NetworkUtils

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        networkUtils = spyk(NetworkUtils(context), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `isNetworkAvailable should return false when activeNetworkInfo is null`() {

        // given
        val activeNetworkInfo: NetworkInfo? = null
        val connectivityManager = mockk<ConnectivityManager>(relaxed = true)
        every { connectivityManager.activeNetworkInfo } returns activeNetworkInfo
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager

        // when
        val result = networkUtils.isNetworkAvailable()

        // then
        assertFalse(result)

        verify {
            networkUtils.isNetworkAvailable()
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
        }
        confirmVerified(networkUtils, context, connectivityManager)
    }

    @Test
    fun `isNetworkAvailable should return false when not connected to internet`() {

        // given
        val activeNetworkInfo = mockk<NetworkInfo>(relaxed = true)
        every { activeNetworkInfo.isConnected } returns false
        val connectivityManager = mockk<ConnectivityManager>(relaxed = true)
        every { connectivityManager.activeNetworkInfo } returns activeNetworkInfo
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager

        // when
        val result = networkUtils.isNetworkAvailable()

        // then
        assertFalse(result)

        verify {
            networkUtils.isNetworkAvailable()
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
            activeNetworkInfo.isConnected
        }
        confirmVerified(networkUtils, context, connectivityManager, activeNetworkInfo)
    }

    @Test
    fun `isNetworkAvailable should return true when connected to internet`() {

        // given
        val activeNetworkInfo = mockk<NetworkInfo>(relaxed = true)
        every { activeNetworkInfo.isConnected } returns true
        val connectivityManager = mockk<ConnectivityManager>(relaxed = true)
        every { connectivityManager.activeNetworkInfo } returns activeNetworkInfo
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager

        // when
        val result = networkUtils.isNetworkAvailable()

        // then
        assertTrue(result)

        verify {
            networkUtils.isNetworkAvailable()
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
            connectivityManager.activeNetworkInfo
            activeNetworkInfo.isConnected
        }
        confirmVerified(networkUtils, context, connectivityManager, activeNetworkInfo)
    }
}
