package com.plumbers.mvvm.data.util

import android.content.Context
import android.content.SharedPreferences
import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppPreferencesUtilTest {
    private lateinit var context: Context
    private lateinit var appPreferencesUtil: AppPreferencesUtil
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun init() {
        context = mockk(relaxed = true)
        sharedPrefs = mockk(relaxed = true)
        editor = mockk(relaxed = true)

        every { sharedPrefs.edit() } answers { editor }

        every { context.getSharedPreferences(any(), any()) } answers { sharedPrefs }

        appPreferencesUtil = AppPreferencesUtil(context)
    }

    @After
    fun teatDown() {
        clearAllMocks()
    }

    @Test
    fun `AppPreferencesUtil should set token`() {

        // Given
        val token = "sample_token"

        // When
        appPreferencesUtil.token = token

        // Then
        verify() {
            context.getSharedPreferences(any(), any())
            sharedPrefs.edit()
            editor.putString(AppPreferencesUtil.Keys.TOKEN.name, token)
            editor.apply()
        }

        confirmVerified(context, sharedPrefs, editor)
    }

    @Test
    fun `AppPreferencesUtil should return token`() {

        // Given
        val token = "sample_token"

        every { sharedPrefs.contains(any()) } answers { true }
        every { sharedPrefs.getString(AppPreferencesUtil.Keys.TOKEN.name, "") } returns token

        // When
        val result = appPreferencesUtil.token

        // then
        assertEquals(token, result)

        verify {
            context.getSharedPreferences(any(), any())
            sharedPrefs.contains(AppPreferencesUtil.Keys.TOKEN.name)
            sharedPrefs.getString(AppPreferencesUtil.Keys.TOKEN.name, "")
        }

        confirmVerified(context, sharedPrefs)
    }
}
