package com.suvojeet.gauravactstudio.util

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_FIRST_LAUNCH = "first_launch"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setLanguage(context: Context, language: String) {
        getPrefs(context).edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun getLanguage(context: Context): String? {
        return getPrefs(context).getString(KEY_LANGUAGE, null)
    }

    fun isFirstLaunch(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(context: Context, isFirst: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_FIRST_LAUNCH, isFirst).apply()
    }
}
