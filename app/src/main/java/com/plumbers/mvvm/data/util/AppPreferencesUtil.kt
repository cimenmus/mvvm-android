package com.plumbers.mvvm.data.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferencesUtil
@Inject constructor(private val context: Context) {

    enum class Keys {
        TOKEN,
    }

    private var sharedPreferences: SharedPreferences? =
        context.getSharedPreferences("movies.sharedprefs", Context.MODE_PRIVATE)

    var token: String?
        get() = getString(Keys.TOKEN)
        set(value) = setString(Keys.TOKEN, value)

    // GETTERS
    private fun getString(key: Keys): String? =
        if (sharedPreferences?.contains(key.name) == true) sharedPreferences?.getString(
            key.name,
            ""
        ) else null

    private fun getFloat(key: Keys): Float? =
        if (sharedPreferences?.contains(key.name) == true) sharedPreferences!!.getFloat(
            key.name,
            0f
        ) else null

    private fun getInt(key: Keys): Int? =
        if (sharedPreferences?.contains(key.name) == true) sharedPreferences!!.getInt(
            key.name,
            0
        ) else null

    private fun getBool(key: Keys): Boolean? =
        if (sharedPreferences?.contains(key.name) == true) sharedPreferences!!.getBoolean(
            key.name,
            false
        ) else null

    private fun getLong(key: Keys): Long? =
        if (sharedPreferences?.contains(key.name) == true) sharedPreferences!!.getLong(
            key.name,
            0
        ) else null

    // SETTERS
    private fun setString(key: Keys, value: String?) {
        val editor = sharedPreferences?.edit()
        value.let { editor?.putString(key.name, value) } ?: remove(key)
        editor?.apply()
    }

    private fun setFloat(key: Keys, value: Float?) {
        val editor = sharedPreferences?.edit()
        value?.let { editor?.putFloat(key.name, value) } ?: remove(key)
        editor?.apply()
    }

    private fun setInt(key: Keys, value: Int?) {
        val editor = sharedPreferences?.edit()
        value?.let { editor?.putInt(key.name, value) } ?: remove(key)
        editor?.apply()
    }

    private fun setBoolean(key: Keys, value: Boolean?) {
        val editor = sharedPreferences?.edit()
        value?.let { editor?.putBoolean(key.name, value) } ?: remove(key)
        editor?.apply()
    }

    private fun setLong(key: Keys, value: Long?) {
        val editor = sharedPreferences?.edit()
        value?.let { editor?.putLong(key.name, value) } ?: remove(key)
        editor?.commit()
    }

    // REMOVE
    private fun remove(key: Keys) {
        val editor = sharedPreferences?.edit()
        editor?.remove(key.name)
        editor?.commit()
    }

}